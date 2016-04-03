package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.UUID;
import javax.validation.Valid;

import com.crossfit.mappers.ProposedWorkoutMapper;
import com.crossfit.model.Exercise;
import com.crossfit.services.ProposedWorkoutService;
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

    @Autowired
    private ProposedWorkoutService proposedWorkoutService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(proposedWorkoutValidator);
    }

    @RequestMapping (value = "/proposedWorkouts", method = POST, produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ProposedWorkoutDTO createProposedWorkout(@Valid @RequestBody ProposedWorkoutDTO proposedWorkout) {
        return proposedWorkoutService.saveProposedWorkout(proposedWorkout);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = GET)
    public ProposedWorkoutDTO getProposedWorkout(@PathVariable("id") String proposedWorkoutId) {
        return proposedWorkoutService.getProposedWorkoutById(proposedWorkoutId);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProposedWorkout(@PathVariable("id") String proposedWorkoutId,
          @Valid @RequestBody ProposedWorkoutDTO proposedWorkoutDTO) {
        proposedWorkoutService.updateProposedWorkout(proposedWorkoutId, proposedWorkoutDTO);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProposedWorkout(@PathVariable("id") String proposedWorkoutId) {
        proposedWorkoutService.deleteProposedWorkout(proposedWorkoutId);
    }
}
