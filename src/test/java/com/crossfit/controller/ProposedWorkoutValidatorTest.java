package com.crossfit.controller;

import static com.crossfit.controller.DtoCreatorUtil.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ProposedWorkoutValidatorTest {
    @Mock
    private ProposedExerciseValidator exerciseValidator;

    @InjectMocks
    private ProposedWorkoutValidator validator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(exerciseValidator.supports(any())).thenReturn(true);
    }

    @Test
    public void given_a_valid_amrap_workout_when_validated_there_are_no_errors() {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        Errors errors = createEmptyErrors(proposedWorkoutDTO);

        validator.validate(proposedWorkoutDTO, errors);

        assertFalse(errors.hasErrors());
    }

    private Errors createEmptyErrors (ProposedWorkoutDTO proposedWorkoutDTO) {return new BeanPropertyBindingResult(proposedWorkoutDTO, "proposedWorkoutDTO");}

    @Test
    public void given_an_amrap_without_durationInSeconds_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = givenAmrapWithoutDurationInSeconds();
        Errors errors = createEmptyErrors(proposedWorkoutDTO);

        validator.validate(proposedWorkoutDTO, errors);

        verifyMissingField(errors, "durationInSeconds", "error.workoutType.amrap.durationInSeconds.null");
    }

    @Test
    public void given_a_valid_forTime_workout_when_validated_there_are_no_errors() {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        Errors errors = createEmptyErrors(proposedWorkoutDTO);

        validator.validate(proposedWorkoutDTO, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void given_a_forTime_without_numberOfRounds_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = DtoCreatorUtil.givenForTimeWithoutNumberOfRounds();
        Errors errors = createEmptyErrors(proposedWorkoutDTO);

        validator.validate(proposedWorkoutDTO, errors);

        verifyMissingField(errors, "numberOfRounds", "error.workoutType.forTime.numberOfRounds.null");
    }

    @Test
    public void given_a_forTime_without_maxAllowedSeconds_when_validated_then_the_validation_fails() {
        ProposedWorkoutDTO proposedWorkoutDTO = DtoCreatorUtil.givenForTimeWithoutMaxAllowedSeconds();
        Errors errors = createEmptyErrors(proposedWorkoutDTO);

        validator.validate(proposedWorkoutDTO, errors);

        verifyMissingField(errors, "maxAllowedSeconds", "error.workoutType.forTime.maxAllowedSeconds.null");
    }

    private void verifyMissingField (Errors errors, String missingField, String expectedMissingFieldMessage) {
        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        assertThat(errors.getFieldError(missingField).getCode(), is(expectedMissingFieldMessage));
    }
}
