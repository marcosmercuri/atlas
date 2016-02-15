package com.crossfit.controller;

import static com.crossfit.controller.TestUtils.*;
import static org.junit.Assert.assertFalse;

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
    public void given_an_exercise_with_one_rx_when_validated_then_validation_fails() {
        ProposedExerciseDTO proposedExercise = createExerciseWithOneRx();
        Errors errors = createEmptyErrors(proposedExercise);

        validator.validate(proposedExercise, errors);

        //TODO Add assertions.
    }

    private Errors createEmptyErrors (ProposedExerciseDTO proposedExercise) {
        return new BeanPropertyBindingResult(proposedExercise, "proposedExerciseDTO");
    }
}
