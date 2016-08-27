package com.crossfit.repositories;

import com.crossfit.model.ResultWorkout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultWorkoutRepository extends CrudRepository<ResultWorkout, String> {
    Long countByProposedWorkoutId(String proposedWorkoutId);
}
