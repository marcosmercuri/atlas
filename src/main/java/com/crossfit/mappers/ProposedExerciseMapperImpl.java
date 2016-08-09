package com.crossfit.mappers;

import static com.crossfit.controller.ExerciseType.*;

import com.crossfit.controller.ProposedExerciseDTO;
import com.crossfit.model.DistanceExercise;
import com.crossfit.model.Exercise;
import com.crossfit.model.RepetitionExercise;
import com.crossfit.model.TimedExercise;
import org.springframework.stereotype.Component;

@Component
class ProposedExerciseMapperImpl implements ProposedExerciseMapper {

    @Override
    public Exercise mapToEntity (ProposedExerciseDTO proposedExerciseDTO) {
        return mapByType(proposedExerciseDTO);
    }

    @Override
    public ProposedExerciseDTO mapToDto (Exercise exercise) {
        if (exercise instanceof RepetitionExercise) {
            return mapRepetitionToDto((RepetitionExercise)exercise);
        } else if (exercise instanceof TimedExercise) {
            return mapTimedToDto((TimedExercise) exercise);
        } else if (exercise instanceof DistanceExercise) {
            return mapDistanceToDto((DistanceExercise) exercise);
        }

        throw new DtoMapperNotAvailableForExerciseException(exercise.getClass().toString());
    }

    private ProposedExerciseDTO mapDistanceToDto (DistanceExercise exercise) {
        ProposedExerciseDTO proposedExerciseDTO = mapCommonFieldsToDto(exercise);
        proposedExerciseDTO.setType(DISTANCE.toString());
        proposedExerciseDTO.setDistanceInMeters(exercise.getDistanceInMeters());
        return proposedExerciseDTO;
    }

    private ProposedExerciseDTO mapTimedToDto (TimedExercise exercise) {
        ProposedExerciseDTO proposedExerciseDTO = mapCommonFieldsToDto(exercise);
        proposedExerciseDTO.setType(TIMED.toString());
        proposedExerciseDTO.setDurationInSeconds(exercise.getDurationInSeconds());
        return proposedExerciseDTO;
    }

    private ProposedExerciseDTO mapRepetitionToDto (RepetitionExercise exercise) {
        ProposedExerciseDTO proposedExerciseDTO = mapCommonFieldsToDto(exercise);
        proposedExerciseDTO.setType(REPETITION.toString());
        proposedExerciseDTO.setNumberOfRepetitions(exercise.getRepetitions());
        return proposedExerciseDTO;
    }

    private ProposedExerciseDTO mapCommonFieldsToDto (Exercise exercise) {
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setId(exercise.getId());
        proposedExerciseDTO.setDescription(exercise.getDescription());
        proposedExerciseDTO.setName(exercise.getName());
        proposedExerciseDTO.setFemaleRxInKilograms(exercise.getFemaleRxInKilograms());
        proposedExerciseDTO.setMaleRxInKilograms(exercise.getMaleRxInKilograms());
        return proposedExerciseDTO;
    }

    private Exercise mapByType (ProposedExerciseDTO proposedExerciseDTO) {
        switch (proposedExerciseDTO.getTypeEnum()) {
            case DISTANCE:
                return mapToDistanceExercise(proposedExerciseDTO);
            case TIMED:
                return mapToTimedExercise(proposedExerciseDTO);
            case REPETITION:
                return mapToRepetitionExercise(proposedExerciseDTO);
            default:
                throw new MapperNotAvailableForProposedExerciseTypeException(proposedExerciseDTO.getTypeEnum());
        }
    }

    private RepetitionExercise mapToRepetitionExercise (ProposedExerciseDTO proposedExerciseDTO) {
        return new RepetitionExercise(
              proposedExerciseDTO.getNumberOfRepetitions(),
              proposedExerciseDTO.getName(),
              proposedExerciseDTO.getId(),
              proposedExerciseDTO.getMaleRxInKilograms(),
              proposedExerciseDTO.getFemaleRxInKilograms(),
              proposedExerciseDTO.getDescription());
    }

    private TimedExercise mapToTimedExercise (ProposedExerciseDTO proposedExerciseDTO) {
        return new TimedExercise(
              proposedExerciseDTO.getDurationInSeconds(),
              proposedExerciseDTO.getName(),
              proposedExerciseDTO.getId(),
              proposedExerciseDTO.getMaleRxInKilograms(),
              proposedExerciseDTO.getFemaleRxInKilograms(),
              proposedExerciseDTO.getDescription());
    }

    private DistanceExercise mapToDistanceExercise (ProposedExerciseDTO proposedExerciseDTO) {
        return new DistanceExercise(
              proposedExerciseDTO.getDistanceInMeters(),
              proposedExerciseDTO.getName(),
              proposedExerciseDTO.getId(),
              proposedExerciseDTO.getMaleRxInKilograms(),
              proposedExerciseDTO.getFemaleRxInKilograms(),
              proposedExerciseDTO.getDescription());
    }
}
