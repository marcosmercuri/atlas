package com.crossfit.services;

import java.util.Optional;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ResultWorkoutNotFoundException;
import com.crossfit.model.ResultWorkout;
import com.crossfit.model.User;
import com.crossfit.repositories.ResultWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ResultWorkoutServiceImpl implements ResultWorkoutService {
    private final ResultWorkoutRepository resultWorkoutRepository;

    @Autowired
    public ResultWorkoutServiceImpl(ResultWorkoutRepository resultWorkoutRepository) {
        this.resultWorkoutRepository = resultWorkoutRepository;
    }

    @Override
    public ResultWorkout saveResultWorkout(ResultWorkout resultWorkout) {
        return resultWorkoutRepository.save(resultWorkout);
    }

    @Override
    public ResultWorkout getResultWorkout(String resultWorkoutId, User user) {
        return resultWorkoutRepository.findByUserIdAndId(user.getId(), resultWorkoutId)
              .orElseThrow(() -> new ResultWorkoutNotFoundException(resultWorkoutId));
    }

    @Override
    public void deleteResultWorkout(String resultWorkoutId, User user) {
        resultWorkoutRepository.findByUserIdAndId(user.getId(), resultWorkoutId)
              .orElseThrow(() -> new ResultWorkoutNotFoundException(resultWorkoutId));
        resultWorkoutRepository.delete(resultWorkoutId);
    }

    @Override
    public void updateResultWorkout(String resultWorkoutId, ResultWorkout resultWorkout) {
        Optional.ofNullable(resultWorkoutRepository.findOne(resultWorkoutId))
              .orElseThrow(() -> new ResultWorkoutNotFoundException(resultWorkoutId));

        if ( ! resultWorkoutId.equals(resultWorkout.getId())) {
            throw new CannotChangeFieldException("id", resultWorkout.getId());
        }
        resultWorkoutRepository.save(resultWorkout);
    }
}
