package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.UUID;
import javax.validation.Valid;

import com.crossfit.model.Exercise;
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
    private ProposedWorkoutValidator proposedWorkoutValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(proposedWorkoutValidator);
    }

    @RequestMapping (value = "/proposedWorkouts", method = POST, produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ProposedWorkoutDTO createProposedWorkout(@Valid @RequestBody ProposedWorkoutDTO proposedWorkout) {
        //saveProposedWorkout = proposedWorkoutManager.saveProposedWorkout(mapper.mapToProposedWorkout(proposedWorkout));
        //return mapper.mapToProposedWorkoutDTO(savedProposedWorkout);
        proposedWorkout.setId(UUID.randomUUID().toString());
        return proposedWorkout;
    }

    @RequestMapping (value = "/proposedWorkouts/{id}/exercises", method = POST)
    public ProposedWorkoutDTO proposedWorkout(@PathVariable("id") Long proposedWorkoutId, @Valid @RequestBody Exercise proposedExercise) {

//        Greeter greeter = new Greeter(counter.getAndIncrement(), String.format(GREETER_TEMPLATE, name));
        return new ProposedWorkoutDTO();
    }
}
