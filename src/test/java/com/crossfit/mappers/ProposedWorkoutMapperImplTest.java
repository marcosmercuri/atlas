package com.crossfit.mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.controller.TestUtils;
import com.crossfit.model.Amrap;
import com.crossfit.model.Exercise;
import com.crossfit.model.ForTimeWorkout;
import com.crossfit.model.Workout;
import org.junit.Before;
import org.junit.Test;

public class ProposedWorkoutMapperImplTest {
    private ProposedWorkoutMapperImpl mapper;

    @Before
    public void setUp() {
        mapper = new ProposedWorkoutMapperImpl(new ProposedExerciseMapperImpl());
    }

    @Test
    public void given_an_amrap_workout_when_map_then_the_entity_has_all_fields_set() {
        ProposedWorkoutDTO amrapDto = TestUtils.givenValidAmrapProposedWorkout(3);

        Workout workout = mapper.mapToEntity(amrapDto);

        assertTrue(workout instanceof Amrap);
        verifyEquality((Amrap) workout, amrapDto);
    }

    private void verifyEquality (Amrap workout, ProposedWorkoutDTO amrapDto) {
        verifyWorkoutCommonFields(workout, amrapDto);
        assertThat(workout.getDurationInSeconds(), is(amrapDto.getDurationInSeconds()));
    }

    @Test
    public void given_a_for_time_workout_when_map_then_the_entity_has_all_fields_set() {
        ProposedWorkoutDTO forTimeWorkoutDto = TestUtils.givenValidForTimeProposedWorkout(2);

        Workout workout = mapper.mapToEntity(forTimeWorkoutDto);

        assertTrue(workout instanceof ForTimeWorkout);
        verifyEquality((ForTimeWorkout) workout, forTimeWorkoutDto);
    }

    private void verifyEquality (ForTimeWorkout workout, ProposedWorkoutDTO forTimeWorkoutDto) {
        verifyWorkoutCommonFields(workout, forTimeWorkoutDto);
        assertThat(workout.getMaxAllowedSeconds(), is(forTimeWorkoutDto.getMaxAllowedSeconds()));
        assertThat(workout.getNumberOfRounds(), is(forTimeWorkoutDto.getNumberOfRounds()));
    }

    private void verifyWorkoutCommonFields (Workout workout, ProposedWorkoutDTO dto) {
        assertThat(workout.getExercises().size(), is(dto.getExercises().size()));
        workout.getExercises().stream().forEach(exercise -> verifyExerciseExistsInWorkoutDto(dto, exercise));
    }

    private void verifyExerciseExistsInWorkoutDto (ProposedWorkoutDTO dto, Exercise exercise) {
        boolean exercisesIsInProposedWorkoutDtp = dto.getExercises()
              .stream()
              .filter(proposedExerciseDto -> areEquals(exercise, proposedExerciseDto))
              .findFirst()
              .isPresent();
        assertTrue(exercisesIsInProposedWorkoutDtp);
    }

    /**
     * I'm comparing by name, based on the assumption that the names of each exercise within a workout
     * are unique.
     */
    private boolean areEquals (Exercise exercise, ProposedExerciseDTO proposedExerciseDto) {
        return exercise.getName().equals(proposedExerciseDto.getName());
    }
}
