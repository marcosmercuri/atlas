package com.crossfit.services;

import java.util.Optional;
import java.util.UUID;

import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.exceptions.ProposedWorkoutNotFound;
import com.crossfit.mappers.ProposedWorkoutMapper;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ProposedWorkoutServiceImpl implements ProposedWorkoutService {
    private final ProposedWorkoutRepository proposedWorkoutRepository;
    private final ProposedWorkoutMapper proposedWorkoutMapper;

    @Autowired
    ProposedWorkoutServiceImpl (ProposedWorkoutRepository proposedWorkoutRepository, ProposedWorkoutMapper proposedWorkoutMapper) {
        this.proposedWorkoutRepository = proposedWorkoutRepository;
        this.proposedWorkoutMapper = proposedWorkoutMapper;
    }

    @Override
    public ProposedWorkoutDTO saveProposedWorkout (ProposedWorkoutDTO proposedWorkoutDto) {
        generateIds(proposedWorkoutDto);
        proposedWorkoutRepository.save(proposedWorkoutMapper.mapToEntity(proposedWorkoutDto));
        return proposedWorkoutDto;
    }

    private void generateIds (ProposedWorkoutDTO proposedWorkoutDto) {
        proposedWorkoutDto.setId(UUID.randomUUID().toString());
        proposedWorkoutDto.getExercises()
              .stream()
              .forEach(proposedExercise -> proposedExercise.setId(UUID.randomUUID().toString()));
    }

    @Override
    public ProposedWorkoutDTO getProposedWorkoutById (String proposedWorkoutId) {
        Optional<Workout> foundProposedWorkout = Optional.ofNullable(proposedWorkoutRepository.findOne(proposedWorkoutId));
        return foundProposedWorkout
              .map(proposedWorkoutMapper::mapToDto)
              .orElseThrow(() -> new ProposedWorkoutNotFound(proposedWorkoutId));
    }
}
