package com.crossfit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

class AuthUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthUtil.class);

    /**
     * Takes a Social Connection and authenticate the user using Spring Security's context.
     * @param connection connection with the user's info
     * @return the logged in userProfile.
     */
    static UserProfile authenticate(Connection<?> connection) {
        UserProfile userProfile = connection.fetchUserProfile();
        String username = userProfile.getUsername();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.info("User {} {} connected.", userProfile.getFirstName(), userProfile.getLastName());
        return userProfile;
    }
}
