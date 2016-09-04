package com.crossfit.security;

import javax.servlet.http.HttpSession;

import com.crossfit.exceptions.UserNotFoundException;
import com.crossfit.model.User;
import com.crossfit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
/**
 * End point to check that a user is logged, and to logout that user.
 */
public class AuthenticationResource {
    private final UserService userService;

    @Autowired
    public AuthenticationResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public User session() {
        Authentication authenticatedUser = SecurityContextHolder.getContext().getAuthentication();
        if(authenticatedUser == null) {
            return null;
        }
        else {
            return userService.getUser(authenticatedUser.getName());
        }
    }

    /**
     * The exception is catch and marked as BadRequest, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "User not found")
    public void userNotFound() {
        // Do nothing
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
