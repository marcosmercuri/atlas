package com.crossfit.controller;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
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
    }

    private void validateRx (Errors errors, ProposedExerciseDTO proposedExercise) {
        if (proposedExercise.getFemaleRxInKilograms()!=null ^ proposedExercise.getMaleRxInKilograms()!=null) {
            errors.reject("error.proposedExercise.rx.oneValueMissing");
        }
    }
}
