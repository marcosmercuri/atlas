package com.crossfit.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import com.crossfit.exceptions.CannotChangeFieldException;
import com.crossfit.exceptions.ProposedWorkoutNotFoundException;
import com.crossfit.exceptions.ResultWorkoutNotFoundException;
import com.crossfit.mappers.ResultWorkoutMapper;
import com.crossfit.model.ResultWorkout;
import com.crossfit.model.Workout;
import com.crossfit.services.ResultWorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/resultWorkouts", produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
class ResultWorkoutController {
    @Autowired
    private ResultWorkoutService resultWorkoutService;
    @Autowired
    private ResultWorkoutMapper mapper;

    @RequestMapping (method = POST)
    @ResponseStatus (HttpStatus.CREATED)
    public ResultWorkoutDTO createResultWorkout(@Valid @RequestBody ResultWorkoutDTO resultWorkoutDTO) {
        ResultWorkout resultWorkout = resultWorkoutService.saveResultWorkout(mapper.mapToEntity(resultWorkoutDTO));
        return mapper.mapToDto(resultWorkout);
    }

    @RequestMapping (value ="/{id}" , method = GET)
    public ResultWorkoutDTO getResultWorkout(@PathVariable("id") String resultWorkoutId) {
        return mapper.mapToDto(resultWorkoutService.getResultWorkout(resultWorkoutId));
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
        resultWorkoutService.deleteResultWorkout(resultWorkoutId);
    }

    @RequestMapping (value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateResultWorkout(@PathVariable("id") String resultWorkoutId,
          @Valid @RequestBody ResultWorkoutDTO resultWorkoutDto) {
        ResultWorkout resultWorkout = mapper.mapToEntity(resultWorkoutDto);
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
