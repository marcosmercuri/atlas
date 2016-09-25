package com.crossfit.services;

import java.util.List;
import java.util.Optional;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutBeingUsedException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import com.crossfit.repositories.ResultWorkoutRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProposedWorkoutServiceImpl implements ProposedWorkoutService {
    private final ProposedWorkoutRepository proposedWorkoutRepository;
    private final ResultWorkoutRepository resultWorkoutRepository;

    @Autowired
    ProposedWorkoutServiceImpl(ProposedWorkoutRepository proposedWorkoutRepository, ResultWorkoutRepository resultWorkoutRepository) {
        this.proposedWorkoutRepository = proposedWorkoutRepository;
        this.resultWorkoutRepository = resultWorkoutRepository;
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

        if ( resultWorkoutRepository.countByProposedWorkoutId(proposedWorkoutId) == 0) {
            proposedWorkoutRepository.delete(proposedWorkoutId);
        } else {
            throw new ProposedWorkoutBeingUsedException(proposedWorkoutId);
        }
    }

    @Override
    public List<Workout> getAllProposedWorkout() {
        return Lists.newArrayList(proposedWorkoutRepository.findAll());
    }
}
