package com.crossfit;

import java.util.Locale;

import com.crossfit.controller.DefaultErrorResponseAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class AtlasApplicationConfiguration {

    @Bean
    public ErrorAttributes errorAttribute () {
        return new DefaultErrorResponseAttributes();
    }

    @Bean (name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource () {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }

    @Bean
    public LocaleResolver localeResolver () {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }
}
