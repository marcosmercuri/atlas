package com.crossfit.repositories;

import com.crossfit.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProposedWorkoutRepository extends MongoRepository<Workout, String> {
}
