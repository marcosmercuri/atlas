package com.crossfit.security;

import javax.sql.DataSource;

import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;

/**
 * Tells spring to use the JdbcUsersConnectionRepository, to store the UsersConnection (ie, user's connection
 * data) in mysql.
 */
class DatabaseSocialConfigurer extends SocialConfigurerAdapter {
    private final DataSource dataSource;

    public DatabaseSocialConfigurer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        TextEncryptor textEncryptor = Encryptors.noOpText();
        return new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, textEncryptor);
    }
}
