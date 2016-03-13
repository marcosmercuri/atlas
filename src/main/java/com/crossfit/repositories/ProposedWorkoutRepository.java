package com.crossfit.repositories;

import com.crossfit.model.Workout;
import org.springframework.data.mongodb.repository.MongoRepository;

//TODO See if it has to be renamed to workoutRepository
public interface ProposedWorkoutRepository extends MongoRepository<Workout, String> {
}
