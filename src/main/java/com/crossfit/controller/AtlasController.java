package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.UUID;
import javax.validation.Valid;

import com.crossfit.model.Exercise;
import com.crossfit.model.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Main controller of the application rest calls
 */
@RestController
public class AtlasController {

    @Autowired
    ProposedWorkoutValidator proposedWorkoutValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(proposedWorkoutValidator);
    }

    @RequestMapping (value = "/proposedWorkouts", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    //TODO Add validations in model
    public ProposedWorkoutDTO createProposedWorkout(@Valid @RequestBody ProposedWorkoutDTO proposedWorkout) {
        proposedWorkout.setId(UUID.randomUUID().toString());
        return proposedWorkout;
    }

    @RequestMapping (value = "/proposedWorkouts/{id}/exercises", method = POST)
    //TODO Conviene crear un request y un response para separar el modelo interno?
    //TODO Add validations in model
    public ProposedWorkoutDTO proposedWorkout(@PathVariable("id") Long proposedWorkoutId, @Valid @RequestBody Exercise proposedExercise) {

//        Greeter greeter = new Greeter(counter.getAndIncrement(), String.format(GREETER_TEMPLATE, name));
        return new ProposedWorkoutDTO();
    }
}
