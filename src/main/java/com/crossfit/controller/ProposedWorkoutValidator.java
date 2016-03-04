package com.crossfit.controller;

import static com.crossfit.controller.WorkoutType.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

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
              proposedExercise -> validateExercise(proposedExercise, errors)
        );
    }

    private void validateExercise(ProposedExerciseDTO proposedExercise, Errors errors) {
        Errors exerciseErrors = new BeanPropertyBindingResult(proposedExercise, errors.getObjectName());
        ValidationUtils.invokeValidator(proposedExerciseValidator, proposedExercise, exerciseErrors);
        errors.addAllErrors(exerciseErrors);
    }

    private void validateForTime (Errors errors) {
        validateNumberOfRounds(errors);
        validateMaxAllowedSeconds(errors);
    }

    private void validateMaxAllowedSeconds (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.forTime", "maxAllowedSeconds");
    }

    private void validateNumberOfRounds (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.forTime", "numberOfRounds");
    }

    private void validateAmrap (Errors errors) {
        validateDurationInSeconds(errors);
    }

    private void validateDurationInSeconds (Errors errors) {
        validateFieldNotEmpty(errors, "error.workoutType.amrap", "durationInSeconds");
    }

    private void validateFieldNotEmpty (Errors errors, String errorCodePrefix, String fieldName) {
        String nullFieldErrorCode = String.format("%s.%s.null", errorCodePrefix, fieldName);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, nullFieldErrorCode);
    }
}
