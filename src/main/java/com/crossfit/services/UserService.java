package com.crossfit.services;

import com.crossfit.model.User;

public interface UserService {
    /**
     * Saves the user. The username must be unique
     * as well as the providerId+providerUserId combination.
     * @param user user to be saved
     */
    void saveUser(User user);

    /**
     * Returns a saved user based on the username.
     * @param username user to retrieve.
     * @return the found user.
     */
    User getUser(String username);
}
