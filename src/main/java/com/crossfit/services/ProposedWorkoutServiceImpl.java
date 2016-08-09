package com.crossfit.services;

import java.util.Optional;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
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
        return proposedWorkoutRepository.save(proposedWorkout);
    }

    @Override
    public Workout getProposedWorkoutById (String proposedWorkoutId) {
        return findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFoundException(proposedWorkoutId));
    }

    private Optional<Workout> findProposedWorkoutById (String proposedWorkoutId) {
        return Optional.ofNullable(proposedWorkoutRepository.findOne(proposedWorkoutId));
    }

    @Override
    public void updateProposedWorkout (String proposedWorkoutId, Workout proposedWorkout) {
        findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFoundException(proposedWorkoutId));

        if ( ! proposedWorkoutId.equals(proposedWorkout.getId())) {
            throw new CannotChangeFieldException("id", proposedWorkout.getId());
        }

        proposedWorkoutRepository.save(proposedWorkout);
    }

    @Override
    public void deleteProposedWorkout (String proposedWorkoutId) {
        findProposedWorkoutById(proposedWorkoutId)
              .orElseThrow(() -> new ProposedWorkoutNotFoundException(proposedWorkoutId));

        proposedWorkoutRepository.delete(proposedWorkoutId);
    }
}
