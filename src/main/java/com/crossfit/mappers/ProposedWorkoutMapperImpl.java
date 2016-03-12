package com.crossfit.mappers;

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

    private ForTimeWorkout mapToForTime (ProposedWorkoutDTO proposedWorkoutDTO) {
        List<Exercise> exercises = mapExercises(proposedWorkoutDTO.getExercises());
        return new ForTimeWorkout(exercises, proposedWorkoutDTO.getMaxAllowedSeconds(), proposedWorkoutDTO.getNumberOfRounds());
    }

    private Amrap mapToAmrap (ProposedWorkoutDTO proposedWorkoutDTO) {
        List<Exercise> exercises = mapExercises(proposedWorkoutDTO.getExercises());
        return new Amrap(exercises, proposedWorkoutDTO.getDurationInSeconds());
    }

    private List<Exercise> mapExercises (List<ProposedExerciseDTO> exercises) {
        return exercises.stream()
        .map(exerciseMapper::mapToEntity)
        .collect(Collectors.toList());
    }
}
