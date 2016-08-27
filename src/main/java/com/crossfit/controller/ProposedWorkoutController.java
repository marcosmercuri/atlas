package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutBeingUsedException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.mappers.ProposedWorkoutMapper;
import com.crossfit.model.Workout;
import com.crossfit.services.ProposedWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Main controller of the application rest calls
 */
@RestController
public class ProposedWorkoutController {

    @Autowired
    private ProposedWorkoutValidator proposedWorkoutValidator;

    @Autowired
    private ProposedWorkoutService proposedWorkoutService;

    @Autowired
    private ProposedWorkoutMapper proposedWorkoutMapper;


    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(proposedWorkoutValidator);
    }

    @RequestMapping (value = "/proposedWorkouts", method = POST, produces = "application/json; charset=utf-8")
    @ResponseStatus(HttpStatus.CREATED)
    public ProposedWorkoutDTO createProposedWorkout(@Valid @RequestBody ProposedWorkoutDTO proposedWorkoutDto) {
        Workout saveProposedWorkout = proposedWorkoutService.saveProposedWorkout(proposedWorkoutMapper.mapToEntity(proposedWorkoutDto));
        return proposedWorkoutMapper.mapToDto(saveProposedWorkout);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = GET, produces = "application/json; charset=utf-8")
    public ProposedWorkoutDTO getProposedWorkout(@PathVariable("id") String proposedWorkoutId) {
        Workout retrievedProposedWorkout = proposedWorkoutService.getProposedWorkoutById(proposedWorkoutId);
        return proposedWorkoutMapper.mapToDto(retrievedProposedWorkout);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProposedWorkout(@PathVariable("id") String proposedWorkoutId,
          @Valid @RequestBody ProposedWorkoutDTO proposedWorkoutDto) {
        Workout proposedWorkoutEntity = proposedWorkoutMapper.mapToEntity(proposedWorkoutDto);
        proposedWorkoutService.updateProposedWorkout(proposedWorkoutId, proposedWorkoutEntity);
    }

    @RequestMapping (value = "/proposedWorkouts/{id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProposedWorkout(@PathVariable("id") String proposedWorkoutId) {
        proposedWorkoutService.deleteProposedWorkout(proposedWorkoutId);
    }

    /**
     * The exception is catch and marked as NotFound, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(ProposedWorkoutNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Proposed workout not found")
    public void proposedWorkoutInResultWorkoutNotFound() {
        // Do nothing
    }

    /**
     * The exception is catch and marked as BadRequest, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(CannotChangeFieldException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Cannot change field value")
    public void cannotChangeFieldValue() {
        // Do nothing
    }


    /**
     * The exception is catch and marked as BadRequest, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(ProposedWorkoutBeingUsedException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Cannot delete proposed workout, as it being used")
    void cannotDeleteProposedWorkoutBeingUsed() {
        // Do nothing
    }
}
