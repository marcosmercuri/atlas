package com.crossfit.services;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.crossfit.exceptions.ProposedWorkoutBeingUsedException;
import com.crossfit.model.Workout;
import com.crossfit.repositories.ProposedWorkoutRepository;
import com.crossfit.repositories.ResultWorkoutRepository;
import org.junit.Before;
import org.junit.Test;

public class ProposedWorkoutServiceImplTest {
    private ProposedWorkoutServiceImpl service;
    private ResultWorkoutRepository resultWorkoutRepository;
    private ProposedWorkoutRepository proposedWorkoutRepository;

    @Before
    public void setUp() {
        resultWorkoutRepository = mock(ResultWorkoutRepository.class);
        proposedWorkoutRepository = mock(ProposedWorkoutRepository.class);
        service = new ProposedWorkoutServiceImpl(proposedWorkoutRepository, resultWorkoutRepository);
    }

    @Test(expected = ProposedWorkoutBeingUsedException.class)
    public void given_proposed_workout_is_referenced_when_delete_is_called_then_an_error_is_thrown() {
        String proposedWorkoutId = "to-be-deleted";
        when(resultWorkoutRepository.countByProposedWorkoutId(eq(proposedWorkoutId))).thenReturn(1L);
        when(proposedWorkoutRepository.findOne(proposedWorkoutId)).thenReturn(mock(Workout.class));

        service.deleteProposedWorkout(proposedWorkoutId);
    }

    @Test
    public void given_proposed_workout_not_referenced_when_delete_is_called_then_deleted_is_called() {
        String proposedWorkoutId = "to-be-deleted";
        when(resultWorkoutRepository.countByProposedWorkoutId(eq(proposedWorkoutId))).thenReturn(0L);
        when(proposedWorkoutRepository.findOne(proposedWorkoutId)).thenReturn(mock(Workout.class));

        service.deleteProposedWorkout(proposedWorkoutId);

        verify(proposedWorkoutRepository).delete(proposedWorkoutId);
    }
}
