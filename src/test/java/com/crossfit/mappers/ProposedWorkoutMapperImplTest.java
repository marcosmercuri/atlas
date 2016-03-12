package com.crossfit.mappers;

import static junit.framework.TestCase.assertTrue;

import com.crossfit.controller.ProposedWorkoutDTO;
import com.crossfit.controller.TestUtils;
import com.crossfit.model.Amrap;
import com.crossfit.model.Workout;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProposedWorkoutMapperImplTest {
    private ProposedWorkoutMapperImpl mapper;

    @Before
    public void setUp() {
        mapper = new ProposedWorkoutMapperImpl();
    }

    @Test
    @Ignore
    public void given_an_amrap_workout_when_map_then_the_entity_has_all_fields_set() {
        ProposedWorkoutDTO amrapDto = TestUtils.givenValidAmrapProposedWorkout(3);

        Workout workout = mapper.mapToEntity(amrapDto);

        assertTrue(workout instanceof Amrap);
        verifyEquality((Amrap) workout, amrapDto);
    }

    private void verifyEquality (Amrap workout, ProposedWorkoutDTO amrapDto) {

    }
}
