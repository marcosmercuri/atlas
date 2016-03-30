package com.crossfit.exceptions;

import java.util.Optional;

/**
 * Exception from which all application exceptions must extend.
 * Take into account that the exception could eventually be converted to a json as a response.
 */
public class BasicException extends RuntimeException {
    private final String userMessage;
    private final String developerMessage;
    private final Integer code;
    private final Optional<Exception> originalException;

    /**
     *
     * @param userMessage Error message that could be shown to a final user of the API's client. Should be in english.
     * @param developerMessage Error message aimed to developers, the API users. It can be technical information that a developer
     *                      calling your API might find useful: might include exception messages, stack traces, etc.
     * @param code An error numeric code that can be use by the API users to reference the problem, and look for help.
     * @param originalException The original caused of the problem (If the exception is wrapping another exception).
     */
    public BasicException (String userMessage, String developerMessage, Integer code, Optional<Exception> originalException) {
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
        this.code = code;
        this.originalException = originalException;
    }

    public BasicException (String userMessage, Integer code, Optional<Exception> originalException) {
        this.userMessage = userMessage;
        this.developerMessage = userMessage;
        this.code = code;
        this.originalException = originalException;
    }

    /**
     * @see #BasicException(String, String, Integer, Optional)
     */
    public String getUserMessage () {
        return userMessage;
    }

    /**
     * @see #BasicException(String, String, Integer, Optional)
     */
    public String getDeveloperMessage () {
        return developerMessage;
    }

    /**
     * @see #BasicException(String, String, Integer, Optional)
     */
    public Integer getCode () {
        return code;
    }

    /**
     * @see #BasicException(String, String, Integer, Optional)
     */
    public Optional<Exception> getOriginalException () {
        return originalException;
    }
}
