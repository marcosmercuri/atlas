package com.crossfit.mappers;

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
        RepetitionExercise repetitionExercise = new RepetitionExercise(proposedExerciseDTO.getNumberOfRepetitions(), proposedExerciseDTO.getName());
        mapCommonExerciseFields(proposedExerciseDTO, repetitionExercise);
        return repetitionExercise;
    }

    private TimedExercise mapToTimedExercise (ProposedExerciseDTO proposedExerciseDTO) {
        TimedExercise timedExercise = new TimedExercise(proposedExerciseDTO.getDurationInSeconds(), proposedExerciseDTO.getName());
        mapCommonExerciseFields(proposedExerciseDTO, timedExercise);
        return timedExercise;
    }

    private DistanceExercise mapToDistanceExercise (ProposedExerciseDTO proposedExerciseDTO) {
        DistanceExercise distanceExercise = new DistanceExercise(proposedExerciseDTO.getDistanceInMeters(), proposedExerciseDTO.getName());
        mapCommonExerciseFields(proposedExerciseDTO, distanceExercise);
        return distanceExercise;
    }

    private void mapCommonExerciseFields (ProposedExerciseDTO sourceDto, Exercise targetEntity) {
        targetEntity.setDescription(sourceDto.getDescription());
        targetEntity.setFemaleRxInKilograms(sourceDto.getFemaleRxInKilograms());
        targetEntity.setMaleRxInKilograms(sourceDto.getMaleRxInKilograms());
    }
}
