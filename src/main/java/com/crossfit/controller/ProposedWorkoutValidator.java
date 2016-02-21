package com.crossfit.controller;

import static com.crossfit.controller.WorkoutType.*;

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

        validateProposedWorkout(errors, (ProposedWorkoutDTO) target);
    }

    private void validateProposedWorkout (Errors errors, ProposedWorkoutDTO proposedWorkout) {
        if (proposedWorkout.getType().equals(AMRAP.toString())) {
            validateAmrap(errors);
        } else if (proposedWorkout.getType().equals(FOR_TIME.toString())) {
            validateForTime(errors);
        }

        validateExerciseList(proposedWorkout, errors);
    }

    private void validateExerciseList (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        proposedWorkout.getExercises().stream().forEach(
              proposedExercise -> proposedExerciseValidator.validate(proposedExercise, errors)
        );
    }

    private void validateForTime (Errors errors) {
        validateNumberOfRounds(errors);
        validateMaxAllowedMinutes(errors);
    }

    private void validateMaxAllowedMinutes (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.forTime", "maxAllowedMinutes");
    }

    private void validateNumberOfRounds (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.forTime", "numberOfRounds");
    }

    private void validateAmrap (Errors errors) {
        validateDurationInMinutes(errors);
    }

    private void validateDurationInMinutes (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.amrap", "durationInMinutes");
    }

    private void validateFieldNotEmpty (Errors errors, String errorCodePrefix, String fieldName) {
        String nullFieldErrorCode = String.format("%s.%s.null", errorCodePrefix, fieldName);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, nullFieldErrorCode);
    }
}
