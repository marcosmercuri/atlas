package com.crossfit.controller;

import static com.crossfit.controller.WorkoutType.*;

import java.util.Optional;

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

    }

    private void validateForTime (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateNumberOfRounds(proposedWorkout, errors);
        validateMaxAllowedMinutes(proposedWorkout, errors);
    }

    private void validateMaxAllowedMinutes (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        String maxAllowedMinutesFieldName = "maxAllowedMinutes";
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, maxAllowedMinutesFieldName, "error.workoutType.forTime.maxAllowedMinutes.null");
        validateFieldIsAboveZero(maxAllowedMinutesFieldName, proposedWorkout.getMaxAllowedMinutes(), errors, "error.workoutType.forTime.maxAllowedMinutes.invalid");
    }

    private void validateNumberOfRounds (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        String numberOfRoundsFieldName = "numberOfRounds";
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, numberOfRoundsFieldName, "error.workoutType.forTime.numberOfRounds.null");
        validateFieldIsAboveZero(numberOfRoundsFieldName, proposedWorkout.getNumberOfRounds(), errors, "error.workoutType.forTime.numberOfRounds.invalid");
    }

    private void validateAmrap (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        validateDurationInMinutes(proposedWorkout, errors);
    }

    private void validateDurationInMinutes (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        String durationInMinutesFieldName = "durationInMinutes";
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, durationInMinutesFieldName, "error.workoutType.amrap.durationInMinutes.null");
        validateFieldIsAboveZero(durationInMinutesFieldName, proposedWorkout.getDurationInMinutes(), errors, "error.workoutType.amrap.durationInMinutes.invalid");
    }

    private void validateFieldIsAboveZero (String fieldName, Integer fieldValue, Errors errors, String errorCode) {
        Optional
              .ofNullable(fieldValue)
              .filter(durationInMinutes -> durationInMinutes <= 0)
              .ifPresent(durationInMinutes -> errors.rejectValue(fieldName, errorCode));
    }
}
