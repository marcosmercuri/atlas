package com.crossfit.util;

class UnableToOpenResourceException extends RuntimeException {

    public UnableToOpenResourceException (String fileName, Exception originalException) {
        super(String.format("Unable to open the file %s", fileName), originalException);
    }
}
