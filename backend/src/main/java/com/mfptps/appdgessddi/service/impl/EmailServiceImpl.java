/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.config.ApplicationProperties;
import com.mfptps.appdgessddi.entities.Agent;
import com.mfptps.appdgessddi.entities.Notification;
import com.mfptps.appdgessddi.entities.NotificationAgent;
import com.mfptps.appdgessddi.repositories.AgentRepository;
import com.mfptps.appdgessddi.repositories.NotificationAgentRepository;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
@Slf4j
@Service
public class EmailServiceImpl {
    
    public final JavaMailSenderImpl javaMailSender;
    public final SpringTemplateEngine templateEngine;
    public final NotificationAgentRepository notificationAgentRepository;
    public final ApplicationProperties applicationProperties;
    public final AgentRepository agentRepository;
	
    public EmailServiceImpl(NotificationAgentRepository notificationAgentRepository
			, JavaMailSenderImpl javaMailSender
			, SpringTemplateEngine templateEngine
			, ApplicationProperties applicationProperties
                        , AgentRepository agentRepository) {
		this.notificationAgentRepository = notificationAgentRepository;
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
		this.applicationProperties = applicationProperties;
                this.agentRepository = agentRepository;
	}
	
	@Async
	public void sendEmail(Notification notification, Agent agent) {
		Context context = new Context();
		context.setVariable("notification", notification);
		String content = templateEngine.process("notification", context);
		String to = agent.getEmail();
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
        	MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, StandardCharsets.UTF_8.name());
        	message.setTo(to);
			message.setFrom(applicationProperties.getMail().getFrom());
			message.setSubject(notification.getObjet());
			message.setText(content, true);
			javaMailSender.send(mimeMessage);
			log.debug("Sent e-mail to User '{}'", to);
		} catch (Exception e) {
			log.warn("E-mail could not be sent to user '{}'", to, e);
		} 
    }
}
