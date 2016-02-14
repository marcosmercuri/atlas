package com.crossfit.controller;

import static com.crossfit.controller.TestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ProposedWorkoutValidatorTest {
    private ProposedWorkoutValidator validator;

    @Before
    public void setUp() {
        validator = new ProposedWorkoutValidator();
    }


    @Test
    public void given_a_valid_amrap_workout_when_validated_there_are_no_errors() {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        Errors errors = new BeanPropertyBindingResult(proposedWorkoutDTO, "proposedWorkoutDTO");

        validator.validate(proposedWorkoutDTO, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void given_an_amrap_without_durationInMinutes_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = givenAmrapWithoutDurationInMinutes();
        Errors errors = new BeanPropertyBindingResult(proposedWorkoutDTO, "proposedWorkoutDTO");

        validator.validate(proposedWorkoutDTO, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("durationInMinutes").getCode(), is("error.workoutType.amrap.durationInMinutes.null"));
    }

    @Test
    public void given_an_amrap_with_invalid_value_durationInMinutes_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = TestUtils.givenAmrapWithInvalidValueDurationInMinutes();
        Errors errors = new BeanPropertyBindingResult(proposedWorkoutDTO, "proposedWorkoutDTO");
        validator.validate(proposedWorkoutDTO, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("durationInMinutes").getCode(), is("error.workoutType.amrap.durationInMinutes.invalid"));
    }

    @Test
    public void given_a_forTime_without_numberOfRounds_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = TestUtils.givenForTimeWithoutNumberOfRounds();
        Errors errors = new BeanPropertyBindingResult(proposedWorkoutDTO, "proposedWorkoutDTO");

        validator.validate(proposedWorkoutDTO, errors);

        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError("numberOfRounds").getCode(), is("error.workoutType.forTime.numberOfRounds.null"));
    }

}
