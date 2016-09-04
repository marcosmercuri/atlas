package com.crossfit.security;

import com.crossfit.exceptions.DuplicatedUsernameException;
import com.crossfit.model.User;
import com.crossfit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * Manages the sign up process of a new user.
 */
@RestController
public class SignupController {
    private final ProviderSignInUtils signInUtils;
    private final UserService userService;

    @Autowired
    public SignupController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository connectionRepository, UserService userService) {
        this.userService = userService;
        this.signInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
    }

    @RequestMapping(value = "/signup")
    public String signup(WebRequest request) {
        Connection<?> connection = signInUtils.getConnectionFromSession(request);
        if(connection != null) {
            UserProfile userProfile = authenticateInSystem(connection);
            saveInSystem(connection, userProfile);
            signInUtils.doPostSignUp(connection.getDisplayName(), request);
        }
        return "redirect:/";
    }

    private UserProfile authenticateInSystem(Connection<?> connection) {
        return AuthUtil.authenticate(connection);
    }

    private void saveInSystem(Connection<?> connection, UserProfile userProfile) {
        User user = new User(userProfile.getUsername(), connection.getKey().getProviderId(),
              connection.getKey().getProviderUserId(), userProfile.getFirstName(),
              userProfile.getLastName(), connection.getImageUrl());
        userService.saveUser(user);
    }

    /**
     * The exception is catch and marked as BadRequest, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(DuplicatedUsernameException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Duplicated user name")
    public void duplicatedUsername() {
        // Do nothing
    }
}
