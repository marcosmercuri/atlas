package com.crossfit.security;

import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
// Excluded in AtlasApplication, and included here, so is it not included in the classpath when the
// profile is test.
@Import(SecurityAutoConfiguration.class)
@Profile("!test")
/**
 * Main configuration for the API authentication
 */
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
              .authorizeRequests()
              .antMatchers("/api/session").permitAll()
              .antMatchers("/h2-console/**").permitAll()
              .antMatchers("/signin/**").permitAll()
              .antMatchers("/signup/**").permitAll()
              .antMatchers("/**").authenticated()
              .and()
              .requestCache()
              .requestCache(new NullRequestCache())
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
              .and().csrf().disable();
    }
}
