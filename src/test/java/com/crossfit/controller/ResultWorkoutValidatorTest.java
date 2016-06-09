package com.crossfit.controller;

import static com.crossfit.util.DtoCreatorUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

public class ResultWorkoutValidatorTest {
    private static final String MOCK_REJECT_MESSAGE = "mock reject message";
    @InjectMocks
    private ResultWorkoutValidator validator;

    @Mock
    private ResultExerciseValidator exerciseValidator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(exerciseValidator.supports(any())).thenReturn(true);
    }

    @Test
    public void given_a_valid_result_workout_when_validated_there_are_no_errors() {
        ResultWorkoutDTO resultWorkoutDTO = createUnfinishedRxResultWorkoutDto();
        Errors errors = callValidator(resultWorkoutDTO);

        verify(exerciseValidator, atLeastOnce()).validate(any(), any());
        assertFalse(errors.hasErrors());
    }

    private Errors callValidator (ResultWorkoutDTO resultWorkoutDTO) {
        Errors errors = createEmptyErrors(resultWorkoutDTO);
        validator.validate(resultWorkoutDTO, errors);
        return errors;
    }

    private Errors createEmptyErrors (ResultWorkoutDTO resultWorkoutDTO) {
        return new BeanPropertyBindingResult(resultWorkoutDTO, "resultWorkoutDTO");
    }

    @Test
    public void given_an_rx_result_workout_has_a_non_rx_result_exercise_then_fails () {
        ResultWorkoutDTO rxWorkoutWithANonRxExercise = createRxResultWorkoutWithANonRxExercise();
        Errors errors = callValidator(rxWorkoutWithANonRxExercise);

        errorContainsOnlyErrorCode(errors, "error.resultWorkout.rx.atLeastOneExerciseIsNotRx");
    }

    private ResultWorkoutDTO createRxResultWorkoutWithANonRxExercise () {
        ResultWorkoutDTO unfinishedRxResultWorkoutDto = createUnfinishedRxResultWorkoutDto();
        unfinishedRxResultWorkoutDto.getResultExercises().get(0).setRx(false);
        return unfinishedRxResultWorkoutDto;
    }

    @Test
    public void given_a_non_rx_result_workout_has_all_rx_exercise_then_fails() {
        ResultWorkoutDTO rxWorkoutWithANonRxExercise = createNonRxResultWorkoutWithAllRxExercise();
        Errors errors = callValidator(rxWorkoutWithANonRxExercise);

        errorContainsOnlyErrorCode(errors, "error.resultWorkout.rx.nonRxHasAllRxExercise");
    }

    private ResultWorkoutDTO createNonRxResultWorkoutWithAllRxExercise () {
        ResultWorkoutDTO unfinishedRxResultWorkoutDto = createUnfinishedRxResultWorkoutDto();
        unfinishedRxResultWorkoutDto.setRx(false);
        return unfinishedRxResultWorkoutDto;
    }

    @Test
    public void given_a_future_result_workout_then_fails() {
        ResultWorkoutDTO rxWorkoutWithANonRxExercise = createResultWorkoutWithFutureDate();
        Errors errors = callValidator(rxWorkoutWithANonRxExercise);

        errorContainsOnlyErrorCode(errors, "error.resultWorkout.date.future");
    }

    private void errorContainsOnlyErrorCode (Errors errors, String errorCode) {
        assertTrue(errors.hasErrors());
        assertThat(errors.getErrorCount(), is(1));
        List<String> errorCodes = errors.getAllErrors().stream().map(DefaultMessageSourceResolvable::getCode).collect(Collectors.toList());
        assertTrue(errorCodes.contains(errorCode));
    }

    private ResultWorkoutDTO createResultWorkoutWithFutureDate () {
        ResultWorkoutDTO unfinishedRxResultWorkoutDto = createUnfinishedRxResultWorkoutDto();
        unfinishedRxResultWorkoutDto.setDate(LocalDate.now().plusMonths(2));
        return unfinishedRxResultWorkoutDto;
    }

    @Test
    public void given_result_exercise_validator_fails_then_fail_workout_validator_fails() {
        ResultWorkoutDTO resultWorkout = createUnfinishedRxResultWorkoutDto();
        Errors errors = createEmptyErrors(resultWorkout);

        createResultWorkoutValidatorWithFailingExerciseValidator().validate(resultWorkout, errors);

        errorContainsOnlyErrorCode(errors, MOCK_REJECT_MESSAGE);
    }

    private ResultWorkoutValidator createResultWorkoutValidatorWithFailingExerciseValidator () {
        ResultExerciseValidator resultExerciseValidator = mock(ResultExerciseValidator.class);
        doAnswer(this::rejectValidationMockedAnswer).doNothing().when(resultExerciseValidator).validate(any(), any());
        when(resultExerciseValidator.supports(any())).thenReturn(true);

        return new ResultWorkoutValidator(resultExerciseValidator);
    }

    private Object rejectValidationMockedAnswer (InvocationOnMock invocationOnMock) {
        Object[] arguments = invocationOnMock.getArguments();
        if (arguments[1] instanceof Errors) {
            Errors errors = (Errors) arguments[1];
            errors.reject(MOCK_REJECT_MESSAGE);
        }
        return null;
    }
}
