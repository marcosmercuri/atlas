package com.crossfit.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.web.ProviderSignInAttempt;

/**
 * Class to stub a connection to a social provider.
 */
class TestProviderSignInAttempt extends ProviderSignInAttempt {
    private Connection<?> connection;

    TestProviderSignInAttempt(Connection<?> connection) {
        super(connection);
        this.connection = connection;
    }

    @Override
    public Connection<?> getConnection(ConnectionFactoryLocator connectionFactoryLocator) {
        return connection;
    }
}
