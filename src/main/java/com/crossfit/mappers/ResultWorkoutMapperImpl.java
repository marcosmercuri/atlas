package com.crossfit.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.crossfit.controller.ResultExerciseDTO;
import com.crossfit.controller.ResultWorkoutDTO;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.model.ResultExercise;
import com.crossfit.model.ResultWorkout;
import com.crossfit.model.ResultWorkoutDetails;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
class ResultWorkoutMapperImpl implements ResultWorkoutMapper {
    private final ResultExerciseMapper resultExerciseMapper;
    private final ProposedWorkoutRepository proposedWorkoutRepository;

    @Autowired
    public ResultWorkoutMapperImpl (ResultExerciseMapper resultExerciseMapper, ProposedWorkoutRepository proposedWorkoutRepository) {
        this.resultExerciseMapper = resultExerciseMapper;
        this.proposedWorkoutRepository = proposedWorkoutRepository;
    }

    @Override
    public ResultWorkout mapToEntity (ResultWorkoutDTO dto, String userId) {
        Assert.hasText(userId);
        Workout proposedWorkout = findProposedRepository(dto.getProposedWorkoutId());
        return new ResultWorkout(dto.getId(), userId, proposedWorkout, mapExercisesToEntity(dto), mapDetails(dto));
    }

    private Workout findProposedRepository (String proposedWorkoutId) {
        Workout proposedWorkout = proposedWorkoutRepository.findOne(proposedWorkoutId);
        if (proposedWorkout == null) {
            throw new ProposedWorkoutNotFoundException(proposedWorkoutId);
        }
        return proposedWorkout;
    }

    private ResultWorkoutDetails mapDetails (ResultWorkoutDTO dto) {
        return new ResultWorkoutDetails(dto.getRx(), dto.getFinished(),
              dto.getFinishTimeInSeconds(), dto.getComments(), dto.getDate());
    }

    private List<ResultExercise> mapExercisesToEntity(ResultWorkoutDTO dto) {
        return dto.getResultExercises()
              .stream()
              .map(resultExerciseMapper::mapToEntity)
              .collect(Collectors.toList());
    }

    @Override
    public ResultWorkoutDTO mapToDto (ResultWorkout resultWorkout) {
        ResultWorkoutDTO dto = new ResultWorkoutDTO();
        dto.setRx(resultWorkout.getDetails().getRx());
        dto.setComments(resultWorkout.getDetails().getComments());
        dto.setDate(resultWorkout.getDetails().getDate());
        dto.setFinished(resultWorkout.getDetails().getFinished());
        dto.setFinishTimeInSeconds(resultWorkout.getDetails().getFinishTimeInSeconds());
        dto.setId(resultWorkout.getId());
        dto.setProposedWorkoutId(resultWorkout.getProposedWorkout().getId());
        dto.setResultExercises(mapExercisesToDto(resultWorkout.getResultExercises()));
        return dto;
    }

    private List<ResultExerciseDTO> mapExercisesToDto(List<ResultExercise> resultExercises) {
        return resultExercises.stream().map(resultExerciseMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ResultWorkoutDTO> mapToDtos(List<ResultWorkout> resultWorkouts){
        return resultWorkouts.stream().map(this::mapToDto).collect(Collectors.toList());
    }
}
