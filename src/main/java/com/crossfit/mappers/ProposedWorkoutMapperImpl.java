package com.crossfit.mappers;

import static com.crossfit.controller.WorkoutType.*;

import java.util.List;
import java.util.stream.Collectors;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.model.Amrap;
import com.crossfit.model.Exercise;
import com.crossfit.model.ForTimeWorkout;
import com.crossfit.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class ProposedWorkoutMapperImpl implements ProposedWorkoutMapper {
    private final ProposedExerciseMapper exerciseMapper;

    @Autowired
    ProposedWorkoutMapperImpl (ProposedExerciseMapper exerciseMapper) {
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public Workout mapToEntity (ProposedWorkoutDTO proposedWorkoutDTO) {
        return mapByType(proposedWorkoutDTO);
    }

    private Workout mapByType (ProposedWorkoutDTO proposedWorkoutDTO) {
        switch (proposedWorkoutDTO.getTypeEnum()) {
            case AMRAP:
                return mapToAmrap(proposedWorkoutDTO);
            case FOR_TIME:
                return mapToForTime(proposedWorkoutDTO);
            default:
                throw new MapperNotAvailableForProposedWorkoutTypeException(proposedWorkoutDTO.getTypeEnum());
        }
    }

    @Override
    public ProposedWorkoutDTO mapToDto (Workout workout) {
        if (workout instanceof Amrap) {
            return mapAmrapToDto((Amrap) workout);
        } else if (workout instanceof ForTimeWorkout) {
            return mapForTimeToDto((ForTimeWorkout) workout);
        }
        throw new DtoMapperNotAvailableForWorkoutException(workout.getClass().toString());
    }

    private ProposedWorkoutDTO mapForTimeToDto (ForTimeWorkout forTimeWorkout) {
        ProposedWorkoutDTO proposedWorkoutDTO = mapWorkoutCommonFieldsToDto(forTimeWorkout);
        proposedWorkoutDTO.setType(AMRAP.toString());
        proposedWorkoutDTO.setMaxAllowedSeconds(forTimeWorkout.getMaxAllowedSeconds());
        proposedWorkoutDTO.setNumberOfRounds(forTimeWorkout.getNumberOfRounds());
        return proposedWorkoutDTO;
    }

    private ProposedWorkoutDTO mapAmrapToDto (Amrap amrap) {
        ProposedWorkoutDTO proposedWorkoutDTO = mapWorkoutCommonFieldsToDto(amrap);
        proposedWorkoutDTO.setType(AMRAP.toString());
        proposedWorkoutDTO.setDurationInSeconds(amrap.getDurationInSeconds());
        return proposedWorkoutDTO;
    }

    /**
     * Maps the workout fields (which are, obviously, common to all workouts) to a dto.
     * The workoutType and it's specific fields are should be set after calling this method.
     */
    private ProposedWorkoutDTO mapWorkoutCommonFieldsToDto (Workout workout) {
        ProposedWorkoutDTO proposedWorkoutDTO = new ProposedWorkoutDTO();
        proposedWorkoutDTO.setId(workout.getId());
        proposedWorkoutDTO.setExercises(mapExercisesToDto(workout.getExercises()));
        return proposedWorkoutDTO;
    }

    private ForTimeWorkout mapToForTime (ProposedWorkoutDTO proposedWorkoutDTO) {
        List<Exercise> exercises = mapExercisesToEntity(proposedWorkoutDTO.getExercises());
        return new ForTimeWorkout(
              exercises,
              proposedWorkoutDTO.getMaxAllowedSeconds(),
              proposedWorkoutDTO.getNumberOfRounds(),
              proposedWorkoutDTO.getId());
    }

    private Amrap mapToAmrap (ProposedWorkoutDTO proposedWorkoutDTO) {
        List<Exercise> exercises = mapExercisesToEntity(proposedWorkoutDTO.getExercises());
        return new Amrap(exercises,
              proposedWorkoutDTO.getDurationInSeconds(),
              proposedWorkoutDTO.getId());
    }

    private List<Exercise> mapExercisesToEntity (List<ProposedExerciseDTO> exercises) {
        return exercises.stream()
        .map(exerciseMapper::mapToEntity)
        .collect(Collectors.toList());
    }

    private List<ProposedExerciseDTO> mapExercisesToDto (List<Exercise> exercises) {
        return exercises.stream()
              .map(exerciseMapper::mapToDto)
              .collect(Collectors.toList());
    }
}
