package com.crossfit.controller;

import static com.crossfit.controller.RequestErrorCodes.INVALID_FIELDS_IN_REQUEST_ERROR_CODE;

import java.util.Optional;
import javax.validation.constraints.NotNull;

import com.crossfit.exceptions.BasicException;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * POJO for a request of proposedExercise
 */
class ProposedExerciseDTO {
    @NotNull(message="error.proposedExercise.name.null")
    @NotBlank(message="error.proposedExercise.name.blank")
    private String name;

    @NotNull(message="error.proposedExercise.type.null")
    private ExerciseType type;

    private String description;
    private Integer maleRxInKilograms;
    private Integer femaleRxInKilograms;
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

    public String getType () {
        return type != null ? type.toString() : null;
    }

    public void setType (String type) {
        try {
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
}
