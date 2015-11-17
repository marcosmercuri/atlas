package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import com.crossfit.model.Exercise;
import com.crossfit.model.Workout;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main controller of the application rest calls
 */
@RestController
//TODO Version via the URL, not via headers
public class AtlasController {

    @RequestMapping (value = "/proposedWorkouts", method = POST)
    //TODO Conviene crear un request y un response para separar el modelo interno?
    //TODO Add validations in model
    public NewProposedWorkoutResponse proposedWorkout(@Valid @RequestBody Workout proposedWorkout) {

//        Greeter greeter = new Greeter(counter.getAndIncrement(), String.format(GREETER_TEMPLATE, name));
        return null;
    }

    @RequestMapping (value = "/proposedWorkouts/{id}/exercises", method = POST)
    //TODO Conviene crear un request y un response para separar el modelo interno?
    //TODO Add validations in model
    public NewProposedWorkoutResponse proposedWorkout(@PathVariable("id") Long proposedWorkoutId, @Valid @RequestBody Exercise proposedExercise) {

//        Greeter greeter = new Greeter(counter.getAndIncrement(), String.format(GREETER_TEMPLATE, name));
        return null;
    }
}
