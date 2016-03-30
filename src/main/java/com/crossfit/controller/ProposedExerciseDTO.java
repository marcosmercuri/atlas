package com.crossfit.controller;

import static com.crossfit.util.RequestErrorCodes.*;

import java.util.Optional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.crossfit.exceptions.BasicException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotBlank;

/**
 * POJO for a request of proposedExercise
 */
public class ProposedExerciseDTO {
    private String id;
    @NotNull(message="error.proposedExercise.name.null")
    @NotBlank(message="error.proposedExercise.name.blank")
    private String name;

    @NotNull(message="error.proposedExercise.type.null")
    private ExerciseType type;

    private String description;

    @Digits (integer=10, fraction=2, message="error.proposedExercise.maleRxInKilograms.exceedDecimals")
    @Min (value = 1, message="error.proposedExercise.maleRxInKilograms.belowMin")
    private Double maleRxInKilograms;

    @Digits (integer=10, fraction=2, message="error.proposedExercise.femaleRxInKilograms.exceedDecimals")
    @Min (value = 1, message="error.proposedExercise.femaleRxInKilograms.belowMin")
    private Double femaleRxInKilograms;

    @Digits (integer=10, fraction=2, message="error.proposedExercise.distanceInMeters.exceedDecimals")
    @Min (value = 1, message="error.proposedExercise.distanceInMeters.belowMin")
    private Double distanceInMeters;

    @Min (value = 1, message="error.proposedExercise.durationInSeconds.belowMin")
    private Integer durationInSeconds;

    @Min (value = 1, message="error.proposedExercise.numberOfRepetitions.belowMin")
    private Integer numberOfRepetitions;

    public Integer getDurationInSeconds () {
        return durationInSeconds;
    }

    public void setDurationInSeconds (Integer durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
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

    public Double getDistanceInMeters () {
        return distanceInMeters;
    }

    public void setDistanceInMeters (Double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public Integer getNumberOfRepetitions () {
        return numberOfRepetitions;
    }

    public void setNumberOfRepetitions (Integer numberOfRepetitions) {
        this.numberOfRepetitions = numberOfRepetitions;
    }

    public String getType () {
        return type != null ? type.toString() : null;
    }

    @JsonIgnore
    public ExerciseType getTypeEnum () {
        return type;
    }

    public void setType (String type) {
        try {
            //TODO Move this into the enum
            this.type = ExerciseType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            throw createInvalidTypeException(exception);
        }
    }

    private BasicException createInvalidTypeException (IllegalArgumentException exception) {
        //I couldn't find a way to get the messageSource injected, to pass the type as argument to the message
        return new BasicException("error.proposedExercise.type.invalid",
              "error.proposedExercise.type.invalid.developerMessage",
              INVALID_FIELDS_IN_REQUEST_ERROR_CODE,
              Optional.of(exception));
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }
}
