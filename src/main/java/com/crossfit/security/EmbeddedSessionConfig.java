package com.crossfit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
// This can be changed to Redis
public class EmbeddedSessionConfig {

    @Bean
    public SessionRepositoryFilter<?> springSessionRepositoryFilter() {
        SessionRepositoryFilter<?> sessionRepositoryFilter = new SessionRepositoryFilter<>(new MapSessionRepository());
        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
        return sessionRepositoryFilter;
    }
}
