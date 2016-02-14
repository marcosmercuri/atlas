package com.crossfit.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestUtils {
    private TestUtils () {
    }

    public static ProposedWorkoutDTO givenValidAmrapProposedWorkout (Integer numberOfExercises) {
        ProposedWorkoutDTO proposedWorkoutDTO = new ProposedWorkoutDTO();
        proposedWorkoutDTO.setType(WorkoutType.AMRAP.name());
        proposedWorkoutDTO.setDurationInMinutes(10);
        proposedWorkoutDTO.setExercises(givenListOfExercises(numberOfExercises));
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenAmrapWithoutDurationInMinutes () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        proposedWorkoutDTO.setDurationInMinutes(null);
        return proposedWorkoutDTO;
    }


    public static ProposedWorkoutDTO givenAmrapWithInvalidValueDurationInMinutes () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidAmrapProposedWorkout(2);
        proposedWorkoutDTO.setDurationInMinutes(0);
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenValidForTimeProposedWorkout (Integer numberOfExercises) {
        ProposedWorkoutDTO proposedWorkoutDTO = new ProposedWorkoutDTO();
        proposedWorkoutDTO.setType(WorkoutType.FOR_TIME.name());
        proposedWorkoutDTO.setNumberOfRounds(10);
        proposedWorkoutDTO.setMaxAllowedMinutes(15);
        proposedWorkoutDTO.setExercises(givenListOfExercises(numberOfExercises));
        return proposedWorkoutDTO;
    }

    public static ProposedWorkoutDTO givenForTimeWithoutNumberOfRounds () {
        ProposedWorkoutDTO proposedWorkoutDTO = givenValidForTimeProposedWorkout(2);
        proposedWorkoutDTO.setNumberOfRounds(null);
        return proposedWorkoutDTO;
    }

    public static List<ProposedExerciseDTO> givenListOfExercises (Integer numberOfExercises) {
        List<ProposedExerciseDTO> exercises = new ArrayList<>(numberOfExercises);
        for (int i = 1; i <= numberOfExercises; i++) {
            exercises.add(createRandomExercise());
        }
        return exercises;
    }

    private static ProposedExerciseDTO createRandomExercise () {
        // For the time being, it's random enough
        ProposedExerciseDTO proposedExerciseDTO = new ProposedExerciseDTO();
        proposedExerciseDTO.setName("Burpees");
        proposedExerciseDTO.setType(getRandomExerciseType());
        proposedExerciseDTO.setDescription("Burpess description");
        proposedExerciseDTO.setNumberOfRepetitions(2);
        return proposedExerciseDTO;
    }

    private static String getRandomExerciseType () {
        List<ExerciseType> teams = new ArrayList<>();
        Collections.addAll(teams, ExerciseType.values());
        Collections.shuffle(teams);
        return teams.get(0).toString();
    }
}
