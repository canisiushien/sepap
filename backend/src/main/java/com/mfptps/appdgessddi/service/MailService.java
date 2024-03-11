package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.config.ApplicationProperties;
import com.mfptps.appdgessddi.entities.Agent;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "agent";

    private static final String BASE_URL = "baseUrl";

    private static final String TITLE = "title";

    private static final String MESSAGE = "message";

    private final ApplicationProperties applicationProperties;

    private final JavaMailSender javaMailSender;

    private final SpringTemplateEngine templateEngine;

    public MailService(
            ApplicationProperties applicationProperties,
            JavaMailSender javaMailSender,
            SpringTemplateEngine templateEngine
    ) {
        this.applicationProperties = applicationProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug(
                "Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
                isMultipart,
                isHtml,
                to,
                subject,
                content
        );

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(applicationProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to Agent '{}'", to);
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to agent '{}'", to, e);
        }
    }

    @Async
    public void sendEmailFromTemplate(Agent agent, String templateName, String titleKey, @Nullable String message) {
        if (agent.getEmail() == null) {
            log.debug("Email doesn't exist for agent '{}'", agent.getMatricule());
            return;
        }
        Context context = new Context();
        context.setVariable(USER, agent);
        context.setVariable(TITLE, titleKey);
        context.setVariable(MESSAGE, message);
        context.setVariable(BASE_URL, applicationProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = titleKey;
        sendEmail(agent.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendActivationEmail(Agent agent) {
        log.debug("Sending activation email to '{}'", agent.getEmail());
        sendEmailFromTemplate(agent, "mail/activationEmail", "activate account", null);
    }

    @Async
    public void sendPasswordResetMail(Agent agent) {
        log.debug("Sending password reset email to '{}'", agent.getEmail());
        sendEmailFromTemplate(agent, "mail/passwordResetEmail", "reset password", null);
    }

    @Async
    public void sendRejetAgentEmail(Agent agent) {
        log.debug("Sending activation email to '{}'", agent.getEmail());
        sendEmailFromTemplate(agent, "mail/rejetNotificationEmail", "Rejet d'affiliation", null);
    }

    @Async
    public void sendNotificationMail(Agent agent, String title, String message) {
        log.debug("Sending activation email to '{}'", agent.getEmail());
        sendEmailFromTemplate(agent, "mail/notificationEmail", title, message);
    }
}
