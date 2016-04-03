package com.crossfit.services;

import java.util.Optional;
import java.util.UUID;

import com.crossfit.exceptions.ProposedWorkoutNotFound;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProposedWorkoutServiceImpl implements ProposedWorkoutService {
    private final ProposedWorkoutRepository proposedWorkoutRepository;

    @Autowired
    ProposedWorkoutServiceImpl (ProposedWorkoutRepository proposedWorkoutRepository) {
        this.proposedWorkoutRepository = proposedWorkoutRepository;
    }

    @Override
    public Workout saveProposedWorkout (Workout proposedWorkout) {
        generateIds(proposedWorkout);
        return proposedWorkoutRepository.save(proposedWorkout);
    }

    private void generateIds (Workout proposedWorkout) {
        proposedWorkout.setId(UUID.randomUUID().toString());
        proposedWorkout.getExercises()
              .stream()
              .forEach(proposedExercise -> proposedExercise.setId(UUID.randomUUID().toString()));
    }

    @Override
    public Workout getProposedWorkoutById (String proposedWorkoutId) {
        return findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFound(proposedWorkoutId));
    }

    private Optional<Workout> findProposedWorkoutById (String proposedWorkoutId) {
        return Optional.ofNullable(proposedWorkoutRepository.findOne(proposedWorkoutId));
    }

    @Override
    public void updateProposedWorkout (String proposedWorkoutId, Workout proposedWorkout) {
        findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFound(proposedWorkoutId));

        proposedWorkoutRepository.save(proposedWorkout);
    }

    @Override public void deleteProposedWorkout (String proposedWorkoutId) {
        findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFound(proposedWorkoutId));

        proposedWorkoutRepository.delete(proposedWorkoutId);
    }
}
