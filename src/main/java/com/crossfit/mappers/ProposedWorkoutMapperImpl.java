package com.crossfit.mappers;

import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.model.Workout;
import org.springframework.stereotype.Component;

@Component
class ProposedWorkoutMapperImpl implements ProposedWorkoutMapper {

    @Override
    public Workout mapToEntity (ProposedWorkoutDTO proposedWorkoutDTO) {
        //Workout workoutEntity = new Workout();
        //TODO magic
        return null;
    }
}
