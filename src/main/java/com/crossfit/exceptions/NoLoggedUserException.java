package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

public class NoLoggedUserException extends BasicException {

    public NoLoggedUserException() {
        super("There's no user logged", NO_LOGGED_USER, Optional.empty());
    }
}
