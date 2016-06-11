package com.crossfit.mappers;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.crossfit.util.DtoCreatorUtil;
import com.crossfit.controller.ResultExerciseDTO;
import com.crossfit.controller.ResultWorkoutDTO;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.model.ResultExercise;
import com.crossfit.model.ResultWorkout;
import com.crossfit.model.ResultWorkoutDetails;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import com.crossfit.util.EntityCreatorUtil;
import org.junit.Before;
import org.junit.Test;

public class ResultWorkoutMapperImplTest {
    private ResultWorkoutMapperImpl resultWorkoutMapper;
    private ProposedWorkoutRepository mockProposedWorkoutRepository;

    @Before
    public void setUp () {
        ResultExerciseMapper exerciseMapper = new ResultExerciseMapperImpl();
        mockProposedWorkoutRepository = mock(ProposedWorkoutRepository.class);
        resultWorkoutMapper = new ResultWorkoutMapperImpl(exerciseMapper, mockProposedWorkoutRepository);
    }

    @Test
    public void given_a_result_workout_dto_when_mapped_the_entity_has_all_the_fields_set () {
        ResultWorkoutDTO dto = DtoCreatorUtil.createFinishedRxResultWorkoutDto();
        givenProposedRepositoryReturnWorkoutForId(dto.getProposedWorkoutId());

        ResultWorkout resultWorkout = resultWorkoutMapper.mapToEntity(dto);

        verifyEquality(resultWorkout, dto);
    }

    private void givenProposedRepositoryReturnWorkoutForId (String proposedWorkoutId) {
        Workout workout = new Workout(proposedWorkoutId, Collections.emptyList());
        when(mockProposedWorkoutRepository.findOne(eq(proposedWorkoutId))).thenReturn(workout);
    }

    private void verifyEquality (ResultWorkout resultWorkout, ResultWorkoutDTO dto) {
        assertThat(resultWorkout.getId(), is(dto.getId()));
        assertThat(resultWorkout.getUserId(), is(dto.getUserId()));
        verifyResultWorkoutDetails(resultWorkout.getDetails(), dto);
        verifyExercises(resultWorkout, dto);
        assertThat(resultWorkout.getProposedWorkout().getId(), is(dto.getProposedWorkoutId()));
    }

    private void verifyExercises (ResultWorkout resultWorkout, ResultWorkoutDTO dto) {
        assertThat(resultWorkout.getResultExercises().size(), is(dto.getResultExercises().size()));
        List<String> resultExerciseIds =
              resultWorkout.getResultExercises().stream().map(ResultExercise::getId).collect(Collectors.toList());

        List<String> resultExerciseDtoIds =
              dto.getResultExercises().stream().map(ResultExerciseDTO::getId).collect(Collectors.toList());

        assertThat(resultExerciseIds.containsAll(resultExerciseDtoIds), is(true));
        assertThat(resultExerciseDtoIds.containsAll(resultExerciseIds), is(true));
    }

    private void verifyResultWorkoutDetails (ResultWorkoutDetails resultWorkoutDetails, ResultWorkoutDTO dto) {
        assertThat(resultWorkoutDetails.getComments(), is(dto.getComments()));
        assertThat(resultWorkoutDetails.getDate(), is(dto.getDate()));
        assertThat(resultWorkoutDetails.getFinished(), is(dto.getFinished()));
        assertThat(resultWorkoutDetails.getFinishTimeInSeconds(), is(dto.getFinishTimeInSeconds()));
        assertThat(resultWorkoutDetails.getRx(), is(dto.getRx()));
    }

    @Test (expected = ProposedWorkoutNotFoundException.class)
    public void given_a_result_workout_dto_with_non_existent_proposed_workout_when_mapped_an_exception_is_thrown () {
        ResultWorkoutDTO dto = DtoCreatorUtil.createFinishedRxResultWorkoutDto();
        givenProposedRepositoryReturnNullForId();

        resultWorkoutMapper.mapToEntity(dto);
    }

    private void givenProposedRepositoryReturnNullForId () {
        when(mockProposedWorkoutRepository.findOne(any())).thenReturn(null);
    }

    @Test
    public void given_a_result_workout_when_mapped_to_dto_all_fields_are_set() {
        ResultWorkout resultWorkout = EntityCreatorUtil.createUnFinishedNonRxResultWorkout();

        ResultWorkoutDTO resultWorkoutDto = resultWorkoutMapper.mapToDto(resultWorkout);

        verifyEquality(resultWorkout, resultWorkoutDto);
    }
}
