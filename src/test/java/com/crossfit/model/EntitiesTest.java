package com.crossfit.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

/**
 * Has all the tests for the equals and hashcode of all the entities.
 * If a new class is added, it should be added here.
 */
public class EntitiesTest {
    @Test
    public void testEqualsOnExerciseHierarchy() {
        verifyEqualsAndHashcode(Exercise.class);
        verifyEqualsAndHashcode(TimedExercise.class);
        verifyEqualsAndHashcode(DistanceExercise.class);
        verifyEqualsAndHashcode(RepetitionExercise.class);
    }

    @Test
    public void testEqualsOnWorkoutHierarchy() {
        verifyEqualsAndHashcode(Amrap.class);
        verifyEqualsAndHashcode(ForTimeWorkout.class);
        verifyEqualsAndHashcode(Workout.class);
    }

    @Test
    public void testEqualsOnResultExerciseHierarchy() {
        verifyEqualsAndHashcode(ResultExercise.class);
        verifyEqualsAndHashcode(RxResultExercise.class);
        verifyEqualsAndHashcode(NonRxResultExercise.class);
    }

    @Test
    public void testEqualsOnResultWorkoutEntities() {
        verifyEqualsAndHashcode(ResultWorkout.class);
        verifyEqualsAndHashcode(RoundsCounter.class);
        verifyEqualsAndHashcode(ResultWorkoutDetails.class);
    }

    private void verifyEqualsAndHashcode(Class clazz) {
        EqualsVerifier.forClass(clazz)
              .usingGetClass()
              .verify();
    }
}
