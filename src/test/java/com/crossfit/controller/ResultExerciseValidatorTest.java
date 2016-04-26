package com.crossfit.controller;

import static com.crossfit.controller.DtoCreatorUtil.createUnfinishedRxRepetitionResultExerciseDto;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ResultExerciseValidatorTest {
    private ResultExerciseValidator validator;

    @Before
    public void setUp() {
        validator = new ResultExerciseValidator();
    }

    private Errors createEmptyErrors (ResultExerciseDTO resultExercise) {
        return new BeanPropertyBindingResult(resultExercise, "resultExerciseDTO");
    }

    @Test
    public void given_a_valid_result_workout_when_validated_there_are_no_errors() {
        ResultExerciseDTO exercise = createUnfinishedRxRepetitionResultExerciseDto();

        Errors errors = callValidator(exercise);

        assertFalse(errors.hasErrors());
    }

    private Errors callValidator (ResultExerciseDTO exercise) {
        Errors errors = createEmptyErrors(exercise);
        validator.validate(exercise, errors);
        return errors;
    }

    @Test
    public void given_rx_with_replace_exercise_validation_fails() {
        ResultExerciseDTO exercise = createRxExerciseWithReplaceExercise();

        Errors errors = callValidator(exercise);

        errorContainsOnlyErrorCode(errors, "error.resultExercise.replaceExercise.cannotBeRx");
    }

    private void errorContainsOnlyErrorCode (Errors errors, String errorCode) {
        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        List<String> errorCodes = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getCode).collect(Collectors.toList());
        assertTrue(errorCodes.contains(errorCode));
    }

    private ResultExerciseDTO createRxExerciseWithReplaceExercise () {
        ResultExerciseDTO exercise = createUnfinishedRxRepetitionResultExerciseDto();
        exercise.setReplaceExercise("replace exercise");
        return exercise;
    }
}
