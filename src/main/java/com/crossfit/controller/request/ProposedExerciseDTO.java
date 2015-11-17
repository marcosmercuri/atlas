package com.crossfit.controller.request;

import javax.validation.constraints.NotNull;

/**
 * POJO for a request of proposedExercise
 */
class ProposedExerciseDTO {
    private Integer maleRxInKilograms;
    private Integer femaleRxInKilograms;
    @NotNull
    private String name;
    private String description;
    private Integer distanceInMeters;
    private Integer durationInMinutes;
    private Integer numberOfRepetitions;

    public Integer getDurationInMinutes () {
        return durationInMinutes;
    }

    public void setDurationInMinutes (Integer durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public Integer getDistanceInMeters () {
        return distanceInMeters;
    }

    public void setDistanceInMeters (Integer distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Integer getFemaleRxInKilograms () {
        return femaleRxInKilograms;
    }

    public void setFemaleRxInKilograms (Integer femaleRxInKilograms) {
        this.femaleRxInKilograms = femaleRxInKilograms;
    }

    public Integer getMaleRxInKilograms () {
        return maleRxInKilograms;
    }

    public void setMaleRxInKilograms (Integer maleRxInKilograms) {
        this.maleRxInKilograms = maleRxInKilograms;
    }

    public Integer getNumberOfRepetitions () {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions (Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }
}
