package com.virtuslab.workshops.kotlin.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Objects;

@Configuration
public class AppConfiguration {

    private final MessageSource messageSource;

    public AppConfiguration(MessageSource messageSource) {
        Objects.requireNonNull(messageSource);
        this.messageSource = messageSource;
    }

    @Bean
    public MessageSourceAccessor messageSourceAccessor() {
        return new MessageSourceAccessor(messageSource);
    }
}
