package com.crossfit.repositories;

import com.crossfit.model.Workout;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProposedWorkoutRepository extends CrudRepository<Workout, String> {
}
