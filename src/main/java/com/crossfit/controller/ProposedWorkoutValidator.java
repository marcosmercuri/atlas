package com.crossfit.controller;

import static com.crossfit.controller.WorkoutType.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for the ProposedWorkoutDTO.
 * Validates that the correct fields are set for each type of WorkoutType.
 */
@Component
class ProposedWorkoutValidator implements Validator {
    @Autowired
    private ProposedExerciseValidator proposedExerciseValidator;

    @Override
    public boolean supports (Class<?> clazz) {
        return ProposedWorkoutDTO.class.equals(clazz);
    }

    @Override
    public void validate (Object target, Errors errors) {
        //If previous validations failed
        if (errors.hasErrors()) {
            return;
        }

        ProposedWorkoutDTO proposedWorkout = (ProposedWorkoutDTO) target;

        if (proposedWorkout.getType().equals(AMRAP.toString())) {
            validateAmrap(proposedWorkout, errors);
        } else if (proposedWorkout.getType().equals(FOR_TIME.toString())) {
            validateForTime(proposedWorkout, errors);
        }

        validateExerciseList(proposedWorkout, errors);
    }

    private void validateExerciseList (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        proposedWorkout.getExercises().stream().forEach(
              proposedExercise -> proposedExerciseValidator.validate(proposedExercise, errors)
        );
    }

    private void validateForTime (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateNumberOfRounds(proposedWorkout, errors);
        validateMaxAllowedMinutes(proposedWorkout, errors);
    }

    private void validateMaxAllowedMinutes (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateIntegerField(errors, "error.workoutType.forTime", "maxAllowedMinutes", proposedWorkout.getMaxAllowedMinutes());
    }

    private void validateNumberOfRounds (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateIntegerField(errors, "error.workoutType.forTime", "numberOfRounds", proposedWorkout.getNumberOfRounds());
    }

    private void validateAmrap (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateDurationInMinutes(proposedWorkout, errors);
    }

    private void validateDurationInMinutes (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateIntegerField(errors, "error.workoutType.amrap", "durationInMinutes", proposedWorkout.getDurationInMinutes());
    }

    private void validateIntegerField (Errors errors, String errorCodePrefix, String fieldName, Integer fieldValue) {
        String nullFieldErrorCode = String.format("%s.%s.null", errorCodePrefix, fieldName);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, nullFieldErrorCode);

        String aboveZeroFieldErrorCode = String.format("%s.%s.invalid", errorCodePrefix, fieldName);
        validateFieldIsAboveZero(fieldName, fieldValue, errors, aboveZeroFieldErrorCode);
    }

    private void validateFieldIsAboveZero (String fieldName, Integer fieldValue, Errors errors, String errorCode) {
        Optional
              .ofNullable(fieldValue)
              .filter(durationInMinutes -> durationInMinutes <= 0)
              .ifPresent(durationInMinutes -> errors.rejectValue(fieldName, errorCode));
    }
}
