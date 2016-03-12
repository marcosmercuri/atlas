package com.crossfit.controller;

import static com.crossfit.controller.ExerciseType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {
    private TestUtils () {
    }

    public static ProposedWorkoutDTO givenValidAmrapProposedWorkout (Integer numberOfExercises) {
        ProposedWorkoutDTO proposedWorkoutDTO = new ProposedWorkoutDTO();
        proposedWorkoutDTO.setType(WorkoutType.AMRAP.name());
        proposedWorkoutDTO.setDurationInSeconds(10);
        proposedWorkoutDTO.setExercises(givenListOfExercises(numberOfExercises));
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenAmrapWithoutDurationInSeconds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        proposedWorkoutDTO.setDurationInSeconds(null);
        return proposedWorkoutDTO;
    }


    public static ProposedWorkoutDTO givenAmrapWithInvalidValueDurationInSeconds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        proposedWorkoutDTO.setDurationInSeconds(0);
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenValidForTimeProposedWorkout (Integer numberOfExercises) {
        ProposedWorkoutDTO proposedWorkoutDTO = new ProposedWorkoutDTO();
        proposedWorkoutDTO.setType(WorkoutType.FOR_TIME.name());
        proposedWorkoutDTO.setNumberOfRounds(10);
        proposedWorkoutDTO.setMaxAllowedSeconds(15);
        proposedWorkoutDTO.setExercises(givenListOfExercises(numberOfExercises));
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenForTimeWithoutNumberOfRounds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        proposedWorkoutDTO.setNumberOfRounds(null);
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenForTimeWithInvalidNumberOfRounds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        proposedWorkoutDTO.setNumberOfRounds(-5);
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenForTimeWithoutMaxAllowedSeconds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        proposedWorkoutDTO.setMaxAllowedSeconds(null);
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenForTimeWithInvalidMaxAllowedSeconds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        proposedWorkoutDTO.setMaxAllowedSeconds(-1);
        return proposedWorkoutDTO;
    }

    public static List<ProposedExerciseDTO> givenListOfExercises (Integer numberOfExercises) {
        List<ProposedExerciseDTO> exercises = new ArrayList<>(numberOfExercises);
        for (int i = 1; i <= numberOfExercises; i++) {
            exercises.add(createRandomValidExercise());
        }
        return exercises;
    }

    public static ProposedExerciseDTO createRandomValidExercise () {
        // For the time being, it's random enough
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setName("Burpees");
        proposedExerciseDTO.setDescription("Burpees description");
        setRandomTypeWithCorrespondingFields(proposedExerciseDTO);
        return proposedExerciseDTO;
    }

    private static void setRandomTypeWithCorrespondingFields(ProposedExerciseDTO proposedExerciseDTO) {
        ExerciseType exerciseType = getRandomExerciseType();
        proposedExerciseDTO.setType(exerciseType.toString());
        switch (exerciseType) {
            case REPETITION:
                proposedExerciseDTO.setNumberOfRepetitions(2);
                break;
            case TIMED:
                proposedExerciseDTO.setDurationInSeconds(200);
                break;
            case DISTANCE:
                proposedExerciseDTO.setDistanceInMeters(2D);
                break;
        }
    }

    private static ExerciseType getRandomExerciseType () {
        List<ExerciseType> teams = new ArrayList<>();
        Collections.addAll(teams, ExerciseType.values());
        Collections.shuffle(teams);
        return teams.get(0);
    }

    public static ProposedExerciseDTO createExerciseWithOnlyFemaleRx () {
        ProposedExerciseDTO randomValidExercise = createExerciseWithBothRx();
        randomValidExercise.setMaleRxInKilograms(null);
        return randomValidExercise;
    }

    public static ProposedExerciseDTO createExerciseWithOnlyMaleRx () {
        ProposedExerciseDTO randomValidExercise = createExerciseWithBothRx();
        randomValidExercise.setFemaleRxInKilograms(null);
        return randomValidExercise;
    }

    public static ProposedExerciseDTO createExerciseWithBothRx() {
        ProposedExerciseDTO randomValidExercise = createRandomValidExercise();
        randomValidExercise.setFemaleRxInKilograms(45D);
        randomValidExercise.setMaleRxInKilograms(50D);
        return randomValidExercise;
    }

    public static ProposedExerciseDTO createDistanceExerciseWithoutDistanceInMeters() {
        ProposedExerciseDTO distanceExercise = createValidDistanceExercise();
        distanceExercise.setDistanceInMeters(null);
        return distanceExercise;
    }

    private static ProposedExerciseDTO createValidDistanceExercise () {
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setType(DISTANCE.toString());
        proposedExerciseDTO.setName("Run");
        proposedExerciseDTO.setDescription("Run description");
        proposedExerciseDTO.setDistanceInMeters(100D);
        return proposedExerciseDTO;
    }

    public static ProposedExerciseDTO createTimedExerciseWithoutDurationInSeconds() {
        ProposedExerciseDTO distanceExercise = createValidTimedExercise();
        distanceExercise.setDurationInSeconds(null);
        return distanceExercise;
    }

    private static ProposedExerciseDTO createValidTimedExercise() {
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setType(TIMED.toString());
        proposedExerciseDTO.setName("burpees");
        proposedExerciseDTO.setDescription("Burpees description");
        proposedExerciseDTO.setDurationInSeconds(120);
        return proposedExerciseDTO;
    }
    public static ProposedExerciseDTO createRepetitionExerciseWithoutNumberOfRepetitions() {
        ProposedExerciseDTO distanceExercise = createRepetitionExercise();
        distanceExercise.setNumberOfRepetitions(null);
        return distanceExercise;
    }

    private static ProposedExerciseDTO createRepetitionExercise() {
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setType(REPETITION.toString());
        proposedExerciseDTO.setName("burpees");
        proposedExerciseDTO.setDescription("Burpees description");
        proposedExerciseDTO.setNumberOfRepetitions(12);
        return proposedExerciseDTO;
    }
}
