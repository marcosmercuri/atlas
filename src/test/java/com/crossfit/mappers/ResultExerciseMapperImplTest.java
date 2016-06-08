package com.crossfit.mappers;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.crossfit.util.DtoCreatorUtil;
import com.crossfit.util.EntityCreatorUtil;
import com.crossfit.controller.ResultExerciseDTO;
import com.crossfit.model.NonRxResultExercise;
import com.crossfit.model.ResultExercise;
import com.crossfit.model.RoundsCounter;
import com.crossfit.model.RxResultExercise;
import org.junit.Before;
import org.junit.Test;

public class ResultExerciseMapperImplTest {
    private ResultExerciseMapperImpl exerciseMapper;

    @Before
    public void setUp () {
        exerciseMapper = new ResultExerciseMapperImpl();
    }

    @Test
    public void given_an_rx_exerciseDTO_when_mapped_then_the_entity_has_all_fields_set () {
        ResultExerciseDTO rxResultExerciseDto = DtoCreatorUtil.createUnfinishedRxRepetitionResultExerciseDto();

        ResultExercise exercise = exerciseMapper.mapToEntity(rxResultExerciseDto);

        assertThat(exercise instanceof RxResultExercise, is(true));
        verifyEquality(exercise, rxResultExerciseDto);
    }

    private void verifyEquality (NonRxResultExercise exercise, ResultExerciseDTO nonRxResultExerciseDto) {
        assertThat(exercise.getWeightInKilograms(), is(nonRxResultExerciseDto.getWeightInKilograms()));
        assertThat(exercise.getReplaceExercise(), is(nonRxResultExerciseDto.getReplaceExercise()));
        verifyMappedRoundsCounter(exercise, nonRxResultExerciseDto);
    }

    private void verifyEquality (ResultExercise exercise, ResultExerciseDTO rxResultExerciseDto) {
        assertThat(exercise.getId(), is(rxResultExerciseDto.getId()));
        assertThat(exercise.getComments(), is(rxResultExerciseDto.getComments()));
        assertThat(exercise.getProposedExerciseId(), is(rxResultExerciseDto.getProposedExerciseId()));
        verifyMappedRoundsCounter(exercise, rxResultExerciseDto);
    }

    private void verifyMappedRoundsCounter (ResultExercise exercise, ResultExerciseDTO rxResultExerciseDto) {
        RoundsCounter roundsCounter = exercise.getRoundsCounter();
        assertThat(roundsCounter.getCompletedRounds(), is(rxResultExerciseDto.getCompletedRounds()));
        assertThat(roundsCounter.getRepetitionsOnUnfinishedRound(), is(rxResultExerciseDto.getRepetitionsOnUnfinishedRound()));
    }

    @Test
    public void given_a_non_rx_exerciseDTO_when_mapped_then_the_entity_has_all_fields_set () {
        ResultExerciseDTO resultExerciseDto = DtoCreatorUtil.createFinishedNonRxResultExerciseDto();

        ResultExercise exercise = exerciseMapper.mapToEntity(resultExerciseDto);

        assertThat(exercise instanceof NonRxResultExercise, is(true));
        verifyEquality((NonRxResultExercise)exercise, resultExerciseDto);
    }

    @Test
    public void given_an_rx_entity_when_mapped_then_the_dto_has_all_fields_set () {
        RxResultExercise exercise = EntityCreatorUtil.createFinishedRxResultExercise();

        ResultExerciseDTO dto = exerciseMapper.mapToDto(exercise);

        verifyEquality(exercise, dto);
    }

    @Test
    public void given_a_non_rx_entity_when_mapped_then_the_dto_has_all_fields_set () {
        NonRxResultExercise exercise = EntityCreatorUtil.createUnFinishedNonRxResultExercise();

        ResultExerciseDTO dto = exerciseMapper.mapToDto(exercise);

        verifyEquality(exercise, dto);
    }
}
