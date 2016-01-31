package com.crossfit.controller;

import static com.crossfit.controller.WorkoutType.*;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
            validateAmrap(errors);
        } else if (proposedWorkout.getType().equals(FOR_TIME.toString())) {
            validateForTime(proposedWorkout, errors);
        }

    }

    private void validateForTime (ProposedWorkoutDTO proposedWorkout, Errors errors) {

    }

    private void validateAmrap (Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durationInMinutes", "error.workoutType.amrap.durationInMinutes.null");
    }
}
