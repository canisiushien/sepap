/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("MFPTPS/ST-GVAP");
        contact.setUrl("http://www.mfptps.gov.bf");
        contact.setEmail("devstgvap@gmail.com");
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("DGESS-DDI API REST")
                        .description("Swagger UI Integration for DGESS-DDI API REST.")
                        .version("1.0.0")
                        .contact(contact));
    }
}
