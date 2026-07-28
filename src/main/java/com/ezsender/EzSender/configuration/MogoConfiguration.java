package com.ezsender.EzSender.configuration;

import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingEntityCallback;

@Configuration
public class MogoConfiguration {

    @Bean
    public ValidatingEntityCallback validatingEntityCallback(Validator validator){
        return new ValidatingEntityCallback(validator);
    }
}
