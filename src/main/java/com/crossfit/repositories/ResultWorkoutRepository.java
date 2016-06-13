package com.crossfit.repositories;

import com.crossfit.model.ResultWorkout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultWorkoutRepository extends MongoRepository<ResultWorkout, String> {
}
