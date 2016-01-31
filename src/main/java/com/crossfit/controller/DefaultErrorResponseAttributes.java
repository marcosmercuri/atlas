package com.crossfit.controller;

import static com.crossfit.controller.RequestErrorCodes.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.crossfit.exceptions.BasicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
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

    @Autowired
    private MessageSource msgSource;

    private class ErrorInformation {
        String message;
        String developerMessage;
        Optional<Integer> errorCode = Optional.empty();
    }

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
        Optional<BasicException> basicException = getBasicExceptionFromError(error);
        if (basicException.isPresent()) {
            return processBasicException(basicException.get());
        } else if (error instanceof MethodArgumentNotValidException) {
            return processInvalidRequestParameter((MethodArgumentNotValidException)error);
        } else {
            return processErrorByDefault(defaultErrorAttributes);
        }
    }

    /**
     * Checks if the error is a BasicException. Also checks that the cause of the error
     * or the cause of the cause of the error is a BasicException.
     * If neither of the above, returns empty optional.
     *
     * This was done because sometimes the exception gets wrapped a few times by spring
     * (eg, validation error of the enum WorkoutType)
     */
    private Optional<BasicException> getBasicExceptionFromError (Throwable error) {
        if (isBasicException(error)) {
            return Optional.of((BasicException)error);
        }
        if (isBasicException(error.getCause())) {
            return Optional.ofNullable((BasicException)error.getCause());
        }
        if (error.getCause()!= null && isBasicException(error.getCause().getCause())) {
            return Optional.ofNullable((BasicException)error.getCause().getCause());
        }
        return Optional.empty();
    }

    private boolean isBasicException (Throwable error) {
        return error instanceof BasicException;
    }

    private ErrorInformation processInvalidRequestParameter (MethodArgumentNotValidException error) {
        ErrorInformation errorInformation = new ErrorInformation();
        errorInformation.errorCode = Optional.of(MISSING_FIELDS_IN_REQUEST_ERROR_CODE);

        errorInformation.message = error.getBindingResult().getFieldErrors()
              .stream()
              .map(this::getErrorMessages)
              .map(this::translateMessages)
              .collect(Collectors.joining(". "));

        errorInformation.developerMessage = error.toString();
        return errorInformation;
    }

    //TODO Move all the translation things to a class of its own
    private String translateMessages (Stream<String> messages) {
        return messages
              .map(this::translateMessage)
              .filter(Optional::isPresent)
              .map(Optional::get)
              .collect(Collectors.joining(". "));
    }

    private Optional<String> translateMessage (String errorMessageKey) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        try {
            return Optional.of(msgSource.getMessage(errorMessageKey, null, currentLocale));
        } catch (NoSuchMessageException ex) {
            return Optional.empty();
        }
    }

    private Stream<String> getErrorMessages (FieldError fieldError) {
        List<String> errors = new ArrayList<>();
        errors.add(fieldError.getDefaultMessage());
        errors.addAll(Arrays.asList(fieldError.getCodes()));
        return errors.stream();
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
