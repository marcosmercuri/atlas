package com.crossfit.model;

/**
 * Represents a possible physical exercise, with it's restriction.
 */
public abstract class Exercise {
    private final String name;
    private Double maleRxInKilograms;
    private Double femaleRxInKilograms;
    private String description;

    public Exercise (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public Double getMaleRxInKilograms () {
        return maleRxInKilograms;
    }

    public void setMaleRxInKilograms (Double maleRxInKilograms) {
        this.maleRxInKilograms = maleRxInKilograms;
    }

    public Double getFemaleRxInKilograms () {
        return femaleRxInKilograms;
    }

    public void setFemaleRxInKilograms (Double femaleRxInKilograms) {
        this.femaleRxInKilograms = femaleRxInKilograms;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
