package com.crossfit.controller;

import static java.time.LocalDate.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

class ResultWorkoutValidator implements Validator {
    private final ResultExerciseValidator resultExerciseValidator;

    @Autowired
    ResultWorkoutValidator (ResultExerciseValidator resultExerciseValidator) {
        this.resultExerciseValidator = resultExerciseValidator;
    }

    @Override
    public boolean supports (Class<?> clazz) {
        return ResultWorkoutDTO.class.equals(clazz);
    }

    @Override
    public void validate (Object target, Errors errors) {
        //If previous validations failed
        if (errors.hasErrors()) {
            return;
        }

        validateResultWorkout((ResultWorkoutDTO)target, errors);
    }

    private void validateResultWorkout (ResultWorkoutDTO resultWorkout, Errors errors) {
        validateRx(resultWorkout, errors);
        validateDateIsNotFuture(resultWorkout, errors);
        validateExerciseList(resultWorkout, errors);
    }

    private void validateExerciseList (ResultWorkoutDTO resultWorkout, Errors errors) {
        resultWorkout.getResultExercises().stream().forEach(
              resultExercise -> validateExercise(resultExercise, errors)
        );
    }

    private void validateExercise (ResultExerciseDTO resultExercise, Errors errors) {
        Errors exerciseErrors = new BeanPropertyBindingResult(resultExercise, errors.getObjectName());
        ValidationUtils.invokeValidator(resultExerciseValidator, resultExercise, exerciseErrors);
        errors.addAllErrors(exerciseErrors);
    }

    private void validateRx (ResultWorkoutDTO resultWorkout, Errors errors) {
        if (resultWorkout.getRx()) {
            validateAllExercisesAreRx(resultWorkout.getResultExercises(), errors);
        } else {
            validateAtLeastOneExerciseIsNonRx(resultWorkout.getResultExercises(), errors);
        }
    }

    private void validateAtLeastOneExerciseIsNonRx (List<ResultExerciseDTO> resultExercises, Errors errors) {
        if ( ! isAnyExerciseNotRx(resultExercises)) {
            errors.reject("error.resultWorkout.rx.nonRxHasAllRxExercise");
        }
    }

    private void validateAllExercisesAreRx (List<ResultExerciseDTO> resultExercises, Errors errors) {
        if (isAnyExerciseNotRx(resultExercises)) {
            errors.reject("error.resultWorkout.rx.atLeastOneExerciseIsNotRx");
        }
    }

    private void validateDateIsNotFuture (ResultWorkoutDTO resultWorkout, Errors errors) {
        if (resultWorkout.getDate().isAfter(now())) {
            errors.reject("error.resultWorkout.date.future");
        }
    }

    private boolean isAnyExerciseNotRx (List<ResultExerciseDTO> resultExercises) {
        return resultExercises.parallelStream()
              .anyMatch(resultExercise -> !resultExercise.getRx());
    }
}
