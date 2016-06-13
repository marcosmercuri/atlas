package com.crossfit.services;

import com.crossfit.model.ResultWorkout;
import com.crossfit.repositories.ResultWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultWorkoutServiceImpl implements ResultWorkoutService {
    private final ResultWorkoutRepository resultWorkoutRepository;

    @Autowired
    public ResultWorkoutServiceImpl(ResultWorkoutRepository resultWorkoutRepository) {
        this.resultWorkoutRepository = resultWorkoutRepository;
    }

    @Override
    public ResultWorkout saveResultWorkout(ResultWorkout resultWorkout) {
        return resultWorkoutRepository.save(resultWorkout);
    }
}
