package com.crossfit.mappers;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.controller.TestUtils;
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
        ProposedExerciseDTO distanceExerciseDto = TestUtils.createValidDistanceExercise();

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
        ProposedExerciseDTO timedExerciseDto = TestUtils.createValidTimedExercise();

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
        ProposedExerciseDTO repetitionExerciseDto = TestUtils.createValidRepetitionExercise();

        Exercise exercise = exerciseMapper.mapToEntity(repetitionExerciseDto);

        assertTrue(exercise instanceof RepetitionExercise);
        verifyEquality((RepetitionExercise) exercise, repetitionExerciseDto);
    }

    private void verifyEquality (RepetitionExercise exercise, ProposedExerciseDTO dto) {
        verifyExerciseCommonFields(exercise, dto);
        assertThat(exercise.getRepetitions(), is(dto.getNumberOfRepetitions()));
    }
}
