package com.crossfit.controller;

/**
 * Represents the different types of exercise, ie, if it's an exercise that has to be repeated a number
 * of times, or if it has to be done for a an amount of time, etc.
 */
enum ExerciseType {
    /**
     * The exercise has to be repeated N times.
     */
    REPETITION,
    /**
     * The exercise has to be done for a certain amount of time.
     */
    TIMED,
    /**
     * The exercise has to be done through a certain distance, such as running.
     */
    DISTANCE
}
