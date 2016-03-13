package com.crossfit.model;

import org.springframework.data.annotation.Id;

/**
 * Represents a physical exercise, with it's restrictions.
 */
public abstract class Exercise {
    @Id
    private final String id;
    private final String name;
    private Double maleRxInKilograms;
    private Double femaleRxInKilograms;
    private String description;

    public Exercise (String id, String name) {
        this.id = id;
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

    public String getId () {
        return id;
    }
}
