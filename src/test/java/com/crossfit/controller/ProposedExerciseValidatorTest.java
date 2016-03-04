package com.crossfit.controller;

import static com.crossfit.controller.TestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ProposedExerciseValidatorTest {
    private ProposedExerciseValidator validator;

    @Before
    public void setUp() {
        this.validator = new ProposedExerciseValidator();
    }

    @Test
    public void given_a_valid_exercise_when_validated_there_are_no_errors() {
        ProposedExerciseDTO proposedExercise = createRandomValidExercise();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void given_an_exercise_with_both_rx_when_validated_there_are_no_errors() {
        ProposedExerciseDTO proposedExercise = createExerciseWithBothRx();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void given_an_exercise_with_female_rx_only_when_validated_then_validation_fails() {
        given_exercise_with_one_rx_when_validated_then_validation_fails(createExerciseWithOnlyFemaleRx());
    }

    @Test
    public void given_an_exercise_with_male_rx_only_when_validated_then_validation_fails() {
        given_exercise_with_one_rx_when_validated_then_validation_fails(createExerciseWithOnlyMaleRx());
    }

    private void given_exercise_with_one_rx_when_validated_then_validation_fails (ProposedExerciseDTO exerciseWithOnlyOneRx) {
        Errors errors = createEmptyErrors(exerciseWithOnlyOneRx);

        validator.validate(exerciseWithOnlyOneRx, errors);

        verifyRxMissingValueError(errors);
    }

    private void verifyRxMissingValueError (Errors errors) {
        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        List<String> errorCodes = errors.getAllErrors().stream().map(error -> error.getCode()).collect(Collectors.toList());
        assertTrue(errorCodes.contains("error.proposedExercise.rx.oneValueMissing"));
    }

    private Errors createEmptyErrors (ProposedExerciseDTO proposedExercise) {
        return new BeanPropertyBindingResult(proposedExercise, "proposedExerciseDTO");
    }

    @Test
    public void given_a_distance_exercise_without_distanceInMeters_when_validated_then_the_validation_fails() {
        ProposedExerciseDTO proposedExercise = createDistanceExerciseWithoutDistanceInMeters();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        verifyMissingField(errors, "distanceInMeters", "error.proposedExercise.distance.distanceInMeters.null");
    }

    @Test
    public void given_a_timed_exercise_without_durationInSeconds_when_validated_then_the_validation_fails() {
        ProposedExerciseDTO proposedExercise = createTimedExerciseWithoutDurationInSeconds();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        verifyMissingField(errors, "durationInSeconds", "error.proposedExercise.timed.durationInSeconds.null");
    }

    @Test
    public void given_a_repetition_exercise_without_numberOfRepetitions_when_validated_then_the_validation_fails() {
        ProposedExerciseDTO proposedExercise = createRepetitionExerciseWithoutNumberOfRepetitions();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        verifyMissingField(errors, "numberOfRepetitions", "error.proposedExercise.repetition.numberOfRepetitions.null");
    }

    private void verifyMissingField (Errors errors, String missingField, String expectedMissingFieldMessage) {
        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), CoreMatchers.is(1));
        assertThat(errors.getFieldError(missingField).getCode(), CoreMatchers.is(expectedMissingFieldMessage));
    }
}
