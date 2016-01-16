package com.crossfit.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.crossfit.exceptions.BasicException;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    private class ErrorInformation {
        String message;
        String developerMessage;
        Optional<Integer> errorCode = Optional.empty();
    }

    private static final int MISSING_FIELDS_IN_REQUEST_ERROR_CODE = 40401;

    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(requestAttributes, includeStackTrace);
        Throwable error = getError(requestAttributes);

        ErrorInformation errorInformation = processError(error, defaultErrorAttributes);

        Map<String, Object> customErrorAttributes = new HashMap<>();
        errorInformation.errorCode.ifPresent(code -> customErrorAttributes.put("code", code));

        customErrorAttributes.put("status", defaultErrorAttributes.get("status"));
        customErrorAttributes.put("message", errorInformation.message);
        customErrorAttributes.put("developerMessage", errorInformation.developerMessage);
        customErrorAttributes.put("timestamp", DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        return customErrorAttributes;
    }

    private ErrorInformation processError(Throwable error, Map<String, Object> defaultErrorAttributes) {
        if (error instanceof BasicException) {
            return processBasicException((BasicException)error);
        } else if (error instanceof MethodArgumentNotValidException) {
            return processInvalidRequestParameter((MethodArgumentNotValidException)error);
        } else {
            return processErrorByDefault(defaultErrorAttributes);
        }
    }

    private ErrorInformation processInvalidRequestParameter (MethodArgumentNotValidException error) {
        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.errorCode = Optional.of(MISSING_FIELDS_IN_REQUEST_ERROR_CODE);

        errorInformation.message = error.getBindingResult().getFieldErrors()
              .stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining(". "));

        errorInformation.developerMessage = error.toString();
        return errorInformation;
    }

    private ErrorInformation processBasicException (BasicException basicException) {
        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.message = basicException.getUserMessage();
        errorInformation.developerMessage = basicException.getDeveloperMessage();
        errorInformation.errorCode = Optional.of(basicException.getCode());
        return errorInformation;
    }

    private ErrorInformation processErrorByDefault (Map<String, Object> defaultErrorAttributes) {
        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.message = (String)defaultErrorAttributes.get("error");
        errorInformation.developerMessage = String.format(
              "Exception: %s. Problem: %s", defaultErrorAttributes.get("exception"), defaultErrorAttributes.get("message")
        );
        return errorInformation;
    }
}
