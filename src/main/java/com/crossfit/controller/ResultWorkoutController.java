package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.exceptions.ResultWorkoutNotFoundException;
import com.crossfit.mappers.ResultWorkoutMapper;
import com.crossfit.model.ResultWorkout;
import com.crossfit.model.User;
import com.crossfit.security.LoggedInUserGetter;
import com.crossfit.services.ResultWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/resultWorkouts", produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
class ResultWorkoutController {
    private ResultWorkoutService resultWorkoutService;
    private ResultWorkoutMapper mapper;
    private LoggedInUserGetter loggedInUserGetter;

    @Autowired
    ResultWorkoutController(ResultWorkoutService resultWorkoutService, ResultWorkoutMapper mapper,
          LoggedInUserGetter loggedInUserGetter) {
        this.resultWorkoutService = resultWorkoutService;
        this.mapper = mapper;
        this.loggedInUserGetter = loggedInUserGetter;
    }

    @RequestMapping (method = POST)
    @ResponseStatus (HttpStatus.CREATED)
    public ResultWorkoutDTO createResultWorkout(@Valid @RequestBody ResultWorkoutDTO resultWorkoutDTO) {
        User user = loggedInUserGetter.getLoggedInUser();
        ResultWorkout resultWorkoutToSave = mapper.mapToEntity(resultWorkoutDTO, user.getId());
        return mapper.mapToDto(resultWorkoutService.saveResultWorkout(resultWorkoutToSave));
    }

    @RequestMapping (value ="/{id}" , method = GET)
    public ResultWorkoutDTO getResultWorkout(@PathVariable("id") String resultWorkoutId) {
        User user = loggedInUserGetter.getLoggedInUser();
        return mapper.mapToDto(resultWorkoutService.getResultWorkout(resultWorkoutId, user));
    }

    /**
     * The exception is catch and marked as BadRequest, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(ProposedWorkoutNotFoundException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason = "Proposed workout not found")
    public void proposedWorkoutInResultWorkoutNotFound() {
        // Do nothing
    }

    /**
     * The exception is catch and marked as NotFound, and rethrown automagically.
     * The reason is needed (although never shown) so DefaultErrorResponseAttributes can process it.
     */
    @ExceptionHandler(ResultWorkoutNotFoundException.class)
    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason = "Result workout not found")
    public void resultWorkoutInResultWorkoutNotFound() {
        // Do nothing
    }

    @RequestMapping (value = "{id}", method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResultWorkout(@PathVariable("id") String resultWorkoutId) {
        User user = loggedInUserGetter.getLoggedInUser();
        resultWorkoutService.deleteResultWorkout(resultWorkoutId, user);
    }

    @RequestMapping (value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResultWorkout(@PathVariable("id") String resultWorkoutId,
          @Valid @RequestBody ResultWorkoutDTO resultWorkoutDto) {
        User user = loggedInUserGetter.getLoggedInUser();
        ResultWorkout resultWorkout = mapper.mapToEntity(resultWorkoutDto, user.getId());
        resultWorkoutService.updateResultWorkout(resultWorkoutId, resultWorkout);
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
}
