package com.crossfit.security;

import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.support.AbstractConnection;

/**
 * Class to stub a connection to a social provider.
 */
class TestConnection extends AbstractConnection {

    private ConnectionData connectionData;

    private UserProfile userProfile;

    public TestConnection(ConnectionData connectionData, UserProfile userProfile) {
        super(connectionData, null);
        this.connectionData = connectionData;
        this.userProfile = userProfile;
    }

    @Override
    public UserProfile fetchUserProfile() {
        return userProfile;
    }

    @Override
    public Object getApi() {
        return null;
    }

    @Override
    public ConnectionData createData() {
        return connectionData;
    }
}
