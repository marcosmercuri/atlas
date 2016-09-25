package com.crossfit.mappers;

import static com.crossfit.controller.WorkoutType.*;
import static com.crossfit.util.EntityCreatorUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.model.Amrap;
import com.crossfit.model.Exercise;
import com.crossfit.model.ForTimeWorkout;
import com.crossfit.model.Workout;
import com.crossfit.util.DtoCreatorUtil;
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
        ProposedWorkoutDTO amrapDto = DtoCreatorUtil.givenValidAmrapProposedWorkout(3);

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
        ProposedWorkoutDTO forTimeWorkoutDto = DtoCreatorUtil.givenValidForTimeProposedWorkout(2);

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
        assertThat(workout.getId(), is(dto.getId()));
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
     * I'm comparing by id, which is unique for each exercise within a workout.
     */
    private boolean areEquals (Exercise exercise, ProposedExerciseDTO proposedExerciseDto) {
        return exercise.getId().equals(proposedExerciseDto.getId());
    }

    @Test
    public void given_an_amrap_workout_when_map_then_the_DTO_has_all_fields_set(){
        Amrap amrap = givenValidAmrap();

        ProposedWorkoutDTO proposedWorkoutDTO = mapper.mapToDto(amrap);

        assertThat(proposedWorkoutDTO.getTypeEnum(), is(AMRAP));
        verifyEquality(amrap, proposedWorkoutDTO);
    }

    @Test
    public void given_an_forTime_workout_when_map_then_the_DTO_has_all_fields_set(){
        ForTimeWorkout forTimeWorkout = givenValidForTimeWorkout();

        ProposedWorkoutDTO proposedWorkoutDTO = mapper.mapToDto(forTimeWorkout);

        assertThat(proposedWorkoutDTO.getTypeEnum(), is(FOR_TIME));
        verifyEquality(forTimeWorkout, proposedWorkoutDTO);
    }

    @Test
    public void given_a_list_of_entities_when_mapped_then_the_dtos_are_returned() {
        List<Workout> workouts = Arrays.asList(givenValidForTimeWorkout(), givenValidAmrap());

        List<ProposedWorkoutDTO> proposedWorkoutDTOs = mapper.mapToDtos(workouts);

        assertThat(proposedWorkoutDTOs.size(), is(2));
        verifyListAreTheSame(workouts, proposedWorkoutDTOs);
    }

    private void verifyListAreTheSame(List<Workout> workouts, List<ProposedWorkoutDTO> proposedWorkoutDTOs) {
        List<String> dtosId = proposedWorkoutDTOs
              .stream()
              .map(ProposedWorkoutDTO::getId)
              .collect(Collectors.toList());

        List<String> entitiesId = workouts
              .stream()
              .map(Workout::getId)
              .collect(Collectors.toList());
        assertTrue(dtosId.contains(entitiesId.get(0)));
        assertTrue(dtosId.contains(entitiesId.get(1)));
    }
}
