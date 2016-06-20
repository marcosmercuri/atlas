package com.crossfit.exceptions;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;

public class CannotChangeFieldException extends BasicException {

    public CannotChangeFieldException(String fieldName, String newFieldValue) {
        super(String.format("Cannot change the field %s with the value %s",  fieldName, newFieldValue),
              CANNOT_CHANGE_FIELD,
              Optional.empty()
        );
    }
}
