package com.crossfit.security;

import com.crossfit.exceptions.NoLoggedUserException;
import com.crossfit.exceptions.UserNotFoundException;
import com.crossfit.model.User;
import com.crossfit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SessionLoggedInUserGetter implements LoggedInUserGetter {
    private UserRepository userRepository;

    @Autowired
    public SessionLoggedInUserGetter(UserRepository userRepository) {this.userRepository = userRepository;}

    /**
     * Gets the name of the logged user from the security context, and retrieves the full user from the database.
     */
    @Override
    public User getLoggedInUser() {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        if(authenticatedUser != null) {
            return getUser(authenticatedUser);
        } else {
            throw new NoLoggedUserException();
        }
    }

    private User getUser(Authentication authenticatedUser) {
        String userName = authenticatedUser.getName();
        return userRepository.findByUsername(userName)
              .orElseThrow(() -> new UserNotFoundException(userName));
    }
}
