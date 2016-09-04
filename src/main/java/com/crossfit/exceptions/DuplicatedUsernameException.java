package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

public class DuplicatedUsernameException extends BasicException {

    public DuplicatedUsernameException(String username) {
        super(String.format("The username %s is duplicated", username),
              DUPLICATED_USERNAME,
              Optional.empty()
        );
    }
}
