package com.crossfit.controller;

import static com.crossfit.controller.ExerciseType.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validator for the ProposedExerciseDTO.
 * Validates that the correct fields are set for each type of ExerciseType.
 */
@Component
class ProposedExerciseValidator implements Validator {

    @Override
    public boolean supports (Class<?> clazz) {
        return ProposedExerciseDTO.class.equals(clazz);
    }

    @Override
    public void validate (Object target, Errors errors) {
        //If previous validations failed
        if (errors.hasErrors()) {
            return;
        }

        validateProposedExercise(errors, (ProposedExerciseDTO) target);
    }

    private void validateProposedExercise (Errors errors, ProposedExerciseDTO proposedExercise) {
        validateRx(errors, proposedExercise);
        if (proposedExercise.getType().equals(DISTANCE.toString())) {
            validateDistanceExercise(errors);
        } else if (proposedExercise.getType().equals(TIMED.toString())) {
            validateTimedExercise(errors);
        } else if (proposedExercise.getType().equals(REPETITION.toString())) {
            validateRepetitionExercise(errors);
        }
    }

    private void validateRepetitionExercise (Errors errors) {
        validateFieldNotEmpty(errors, "error.proposedExercise.repetition", "numberOfRepetitions");
    }

    private void validateTimedExercise (Errors errors) {
        validateFieldNotEmpty(errors, "error.proposedExercise.timed", "durationInSeconds");
    }

    private void validateDistanceExercise (Errors errors) {
        validateFieldNotEmpty(errors, "error.proposedExercise.distance", "distanceInMeters");
    }

    private void validateRx (Errors errors, ProposedExerciseDTO proposedExercise) {
        if (proposedExercise.getFemaleRxInKilograms()!=null ^ proposedExercise.getMaleRxInKilograms()!=null) {
            errors.reject("error.proposedExercise.rx.oneValueMissing");
        }
    }

    private void validateFieldNotEmpty (Errors errors, String errorCodePrefix, String fieldName) {
        String nullFieldErrorCode = String.format("%s.%s.null", errorCodePrefix, fieldName);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, nullFieldErrorCode);
    }
}
