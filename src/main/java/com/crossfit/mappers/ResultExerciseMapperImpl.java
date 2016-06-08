package com.crossfit.mappers;

import com.crossfit.controller.ResultExerciseDTO;
import com.crossfit.model.NonRxResultExercise;
import com.crossfit.model.ResultExercise;
import com.crossfit.model.RoundsCounter;
import com.crossfit.model.RxResultExercise;
import org.springframework.stereotype.Component;

@Component
class ResultExerciseMapperImpl implements ResultExerciseMapper {

    @Override
    public ResultExercise mapToEntity (ResultExerciseDTO resultExerciseDTO) {
        if (resultExerciseDTO.getRx()) {
            return mapToRx(resultExerciseDTO);
        }
        else {
            return mapToNonRx(resultExerciseDTO);
        }
    }

    private ResultExercise mapToNonRx (ResultExerciseDTO resultExerciseDTO) {
        return new NonRxResultExercise(resultExerciseDTO.getId(),
              resultExerciseDTO.getProposedExerciseId(),
              mapToRoundsCounter(resultExerciseDTO),
              resultExerciseDTO.getComments(),
              resultExerciseDTO.getWeightInKilograms(),
              resultExerciseDTO.getReplaceExercise());
    }

    private ResultExercise mapToRx (ResultExerciseDTO resultExerciseDTO) {
        RxResultExercise resultExercise =
              new RxResultExercise(resultExerciseDTO.getId(),
                    resultExerciseDTO.getProposedExerciseId(),
                    mapToRoundsCounter(resultExerciseDTO),
                    resultExerciseDTO.getComments());
        return resultExercise;
    }

    private RoundsCounter mapToRoundsCounter (ResultExerciseDTO resultExerciseDTO) {return new RoundsCounter(resultExerciseDTO.getCompletedRounds(), resultExerciseDTO.getRepetitionsOnUnfinishedRound());}

    @Override
    public ResultExerciseDTO mapToDto (ResultExercise exercise) {
        if (exercise instanceof RxResultExercise) {
            return mapRxToDto((RxResultExercise)exercise);
        }
        else if (exercise instanceof NonRxResultExercise) {
            return mapNonRxToDto((NonRxResultExercise)exercise);
        }

        throw new DtoMapperNotAvailableForExerciseException(exercise.getClass().toString());
    }

    private ResultExerciseDTO mapNonRxToDto (NonRxResultExercise exercise) {
        ResultExerciseDTO dto = mapBasicEntityInfoToDto(exercise);
        dto.setWeightInKilograms(exercise.getWeightInKilograms());
        dto.setReplaceExercise(exercise.getReplaceExercise());
        return dto;
    }

    public ResultExerciseDTO mapRxToDto (RxResultExercise exercise) {
        return mapBasicEntityInfoToDto(exercise);
    }

    private ResultExerciseDTO mapBasicEntityInfoToDto (ResultExercise exercise) {
        ResultExerciseDTO dto = new ResultExerciseDTO();
        dto.setRx(true);
        dto.setId(exercise.getId());
        dto.setComments(exercise.getComments());
        dto.setProposedExerciseId(exercise.getProposedExerciseId());

        RoundsCounter roundsCounter = exercise.getRoundsCounter();
        dto.setCompletedRounds(roundsCounter.getCompletedRounds());
        dto.setRepetitionsOnUnfinishedRound(roundsCounter.getRepetitionsOnUnfinishedRound());

        return dto;
    }
}
