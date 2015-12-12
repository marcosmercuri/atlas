package com.crossfit.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.crossfit.exceptions.BasicException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.context.request.RequestAttributes;

/**
 * Custom error response class, that decorates the default error response structure of spring.
 * This way if the default spring response changes, the API will remain unchanged.
 * The response will be like this:
 * {
 * "status": 404,
 * "code": 40483,
 * "message": "Oops! It looks like that file does not exist.",
 * "developerMessage": "File resource for path /uploads/foobar.txt does not exist.  Please wait 10 minutes until the upload batch completes before checking again.",
 * "timestamp": 2015-12-03T00:11:05.211
 * }
 */
public class DefaultErrorResponseAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Throwable error = getError(requestAttributes);

        String message;
        String developerMessage;
        Optional<Integer> errorCode = Optional.empty();
        if (error instanceof BasicException) {
            BasicException basicException = (BasicException) error;
            message = basicException.getUserMessage();
            developerMessage = basicException.getDeveloperMessage();
            errorCode = Optional.of(basicException.getCode());
        } else {
            message = (String)defaultErrorAttributes.get("error");
            developerMessage = String.format(
                  "Exception: %s. Problem: %s", defaultErrorAttributes.get("exception"), defaultErrorAttributes.get("message")
            );
        }

        Map<String, Object> customErrorAttributes = new HashMap<>();
        errorCode.ifPresent(code -> customErrorAttributes.put("code", code));

        customErrorAttributes.put("status", defaultErrorAttributes.get("status"));
        customErrorAttributes.put("message", message);
        customErrorAttributes.put("developerMessage", developerMessage);
        customErrorAttributes.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        return customErrorAttributes;
    }
}
