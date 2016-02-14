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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numberOfRounds", "error.workoutType.forTime.numberOfRounds.null");
    }

    private void validateAmrap (ProposedWorkoutDTO proposedWorkout, Errors errors) {

        validateDurationInMinutes(proposedWorkout, errors);


    }

    private void validateDurationInMinutes (ProposedWorkoutDTO proposedWorkout, Errors errors) {
        String durationInMinutesFieldName = "durationInMinutes";
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, durationInMinutesFieldName, "error.workoutType.amrap.durationInMinutes.null");

        Optional
              .ofNullable(proposedWorkout.getDurationInMinutes())
              .filter(durationInMinutes -> durationInMinutes <= 0)
              .ifPresent(durationInMinutes -> errors.rejectValue(durationInMinutesFieldName, "error.workoutType.amrap.durationInMinutes.invalid"));
    }
}
