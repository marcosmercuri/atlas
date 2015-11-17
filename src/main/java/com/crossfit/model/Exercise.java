package com.crossfit.model;

/**
 * Represents a possible physical exercise, with it's restriction.
 */
public class Exercise {
    private Integer maleRxInKilograms;
    private Integer femaleRxInKilograms;
    private String name;
    private String description;

    public Exercise (Integer maleRxInKilograms, Integer femaleRxInKilograms, String name, String description) {
        this.maleRxInKilograms = maleRxInKilograms;
        this.femaleRxInKilograms = femaleRxInKilograms;
        this.name = name;
        this.description = description;
    }

    public Integer getMaleRxInKilograms () {
        return maleRxInKilograms;
    }

    public Integer getFemaleRxInKilograms () {
        return femaleRxInKilograms;
    }

    public String getName () {
        return name;
    }

    public String getDescription () {
        return description;
    }
}
