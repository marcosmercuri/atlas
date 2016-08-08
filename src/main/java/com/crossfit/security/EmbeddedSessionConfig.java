package com.crossfit.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

@Configuration
@Profile("!test")
// This can be changed for a Redis
/**
 * The session is linked to the x-auth-token header, and it is store in an on-memory redis like ddbb.
 * All magic's done by Spring-session.
 */
public class EmbeddedSessionConfig {

    @Bean
    public SessionRepositoryFilter<?> springSessionRepositoryFilter() {
        SessionRepositoryFilter<?> sessionRepositoryFilter = new SessionRepositoryFilter<>(new MapSessionRepository());
        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
        return sessionRepositoryFilter;
    }
}
