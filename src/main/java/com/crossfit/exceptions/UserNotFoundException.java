package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

public class UserNotFoundException extends BasicException {

    public UserNotFoundException(String username) {
        super(String.format("There's no user with username %s", username),
              USER_NOT_FOUND,
              Optional.empty()
        );
    }
}
