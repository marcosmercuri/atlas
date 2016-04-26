package com.crossfit.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

class ResultExerciseValidator implements Validator {

    @Override
    public boolean supports (Class<?> clazz) {
        return ResultExerciseDTO.class.equals(clazz);
    }

    @Override
    public void validate (Object target, Errors errors) {
        //If previous validations failed
        if (errors.hasErrors()) {
            return;
        }

        validateResultExercise((ResultExerciseDTO) target, errors);
    }

    private void validateResultExercise (ResultExerciseDTO resultExercise, Errors errors) {
        if (resultExercise.getRx() && StringUtils.isNotEmpty(resultExercise.getReplaceExercise())) {
            errors.reject("error.resultExercise.replaceExercise.cannotBeRx");
        }
    }
}
