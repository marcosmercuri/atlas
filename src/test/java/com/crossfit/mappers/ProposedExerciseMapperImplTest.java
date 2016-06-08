package com.crossfit.mappers;

import static com.crossfit.controller.ExerciseType.*;
import static junit.framework.TestCase.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import com.crossfit.util.DtoCreatorUtil;
import com.crossfit.util.EntityCreatorUtil;
import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.model.DistanceExercise;
import com.crossfit.model.Exercise;
import com.crossfit.model.RepetitionExercise;
import com.crossfit.model.TimedExercise;
import org.junit.Before;
import org.junit.Test;

public class ProposedExerciseMapperImplTest {
    private ProposedExerciseMapperImpl exerciseMapper;

    @Before
    public void setUp() {
        exerciseMapper = new ProposedExerciseMapperImpl();
    }

    @Test
    public void given_a_distance_proposedExerciseDTO_when_map_then_the_entity_has_all_fields_set() {
        ProposedExerciseDTO distanceExerciseDto = DtoCreatorUtil.createValidDistanceExercise();

        Exercise exercise = exerciseMapper.mapToEntity(distanceExerciseDto);

        assertTrue(exercise instanceof DistanceExercise);
        verifyEquality((DistanceExercise) exercise, distanceExerciseDto);
    }

    private void verifyEquality (DistanceExercise exercise, ProposedExerciseDTO dto) {
        verifyExerciseCommonFields(exercise, dto);
        assertThat(exercise.getDistanceInMeters(), is(dto.getDistanceInMeters()));
    }

    private void verifyExerciseCommonFields (Exercise exercise, ProposedExerciseDTO dto) {
        assertThat(exercise.getMaleRxInKilograms(), is(dto.getMaleRxInKilograms()));
        assertThat(exercise.getFemaleRxInKilograms(), is(dto.getFemaleRxInKilograms()));
        assertThat(exercise.getDescription(), is(dto.getDescription()));
        assertThat(exercise.getName(), is(dto.getName()));
        assertThat(exercise.getId(), is(dto.getId()));
    }

    @Test
    public void given_a_timed_proposedExerciseDTO_when_map_then_the_entity_has_all_fields_set() {
        ProposedExerciseDTO timedExerciseDto = DtoCreatorUtil.createValidTimedExercise();

        Exercise exercise = exerciseMapper.mapToEntity(timedExerciseDto);

        assertTrue(exercise instanceof TimedExercise);
        verifyEquality((TimedExercise) exercise, timedExerciseDto);
    }

    private void verifyEquality (TimedExercise exercise, ProposedExerciseDTO dto) {
        verifyExerciseCommonFields(exercise, dto);
        assertThat(exercise.getDurationInSeconds(), is(dto.getDurationInSeconds()));
    }

    @Test
    public void given_a_repetition_proposedExerciseDTO_when_map_then_the_entity_has_all_fields_set() {
        ProposedExerciseDTO repetitionExerciseDto = DtoCreatorUtil.createValidRepetitionExercise();

        Exercise exercise = exerciseMapper.mapToEntity(repetitionExerciseDto);

        assertTrue(exercise instanceof RepetitionExercise);
        verifyEquality((RepetitionExercise) exercise, repetitionExerciseDto);
    }

    private void verifyEquality (RepetitionExercise exercise, ProposedExerciseDTO dto) {
        verifyExerciseCommonFields(exercise, dto);
        assertThat(exercise.getRepetitions(), is(dto.getNumberOfRepetitions()));
    }

    @Test
    public void given_a_repetitionExercise_when_map_then_the_dto_has_all_fields_set() {
        RepetitionExercise repetitionExercise = EntityCreatorUtil.createValidRepetitionExercise();

        ProposedExerciseDTO proposedExerciseDTO = exerciseMapper.mapToDto(repetitionExercise);

        assertThat(proposedExerciseDTO.getTypeEnum(), is(REPETITION));
        verifyEquality(repetitionExercise, proposedExerciseDTO);
    }

    @Test
    public void given_a_timedExercise_when_map_then_the_dto_has_all_fields_set() {
        TimedExercise timedExercise = EntityCreatorUtil.createValidTimedExercise();

        ProposedExerciseDTO proposedExerciseDTO = exerciseMapper.mapToDto(timedExercise);

        assertThat(proposedExerciseDTO.getTypeEnum(), is(TIMED));
        verifyEquality(timedExercise, proposedExerciseDTO);
    }

    @Test
    public void given_a_distanceExercise_when_map_then_the_dto_has_all_fields_set() {
        DistanceExercise distanceExercise = EntityCreatorUtil.createValidDistanceExercise();

        ProposedExerciseDTO proposedExerciseDTO = exerciseMapper.mapToDto(distanceExercise);

        assertThat(proposedExerciseDTO.getTypeEnum(), is(DISTANCE));
        verifyEquality(distanceExercise, proposedExerciseDTO);
    }
}
