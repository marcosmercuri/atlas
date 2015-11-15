package com.crossfit.model;

import java.util.List;

/**
 *
 */
public class Amrap extends Workout {
    private Integer durationInMinutes;

    public Amrap (List<Task> task, Integer durationInMinutes) {
        super(task);
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getDurationInMinutes () {
        return durationInMinutes;
    }
}
