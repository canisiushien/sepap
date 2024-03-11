package com.mfptps.appdgessddi.config;

import com.mfptps.appdgessddi.aop.LoggingAspect;
import com.mfptps.appdgessddi.utils.AppProfile;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(AppProfile.DEVELOPMENT)
    public LoggingAspect loggingAspect(Environment env) {
        return new LoggingAspect(env);
    }
}
