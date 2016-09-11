package com.crossfit.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.web.SignInAdapter;

@Configuration
@Profile("!integration-test")
public class SocialConfiguration {

    @Bean
    public SignInAdapter authSignInAdapter() {
        // Authenticates the user on signIn
        return (userId, connection, request) -> {
            AuthUtil.authenticate(connection);
            return null;
        };
    }

    @Bean
    public SocialConfigurer socialConfigurerAdapter(DataSource dataSource) {
        return new DatabaseSocialConfigurer(dataSource);
    }
}
