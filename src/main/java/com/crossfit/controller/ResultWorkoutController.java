package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/resultWorkouts")
class ResultWorkoutController {

    @RequestMapping (method = POST, produces = "application/json; charset=utf-8")
    @ResponseStatus (HttpStatus.CREATED)
    public ResultWorkoutDTO createResultWorkout(@Valid @RequestBody ResultWorkoutDTO resultWorkoutDTO) {
        return resultWorkoutDTO;
    }
}
