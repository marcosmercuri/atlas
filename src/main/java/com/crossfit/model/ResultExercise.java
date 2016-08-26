package com.crossfit.model;

import java.util.UUID;
import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public abstract class ResultExercise {
    @Id
    private String id;
    private final String proposedExerciseId;
    @Embedded
    private final RoundsCounter roundsCounter;
    private final String comments;

    // This is needed for spring to hydrate the object
    protected ResultExercise() {
        this(null, null, null, null);
    }

    protected ResultExercise (String id, String proposedExerciseId, RoundsCounter roundsCounter, String comments) {
        this.id = id;
        this.proposedExerciseId = proposedExerciseId;
        this.roundsCounter = roundsCounter;
        this.comments = comments;
    }

    /**
     * It is only executed on insert, and generates an UUID for the entity
     */
    @PrePersist
    private void autoGenerateId(){
        this.id = UUID.randomUUID().toString();
    }

    public String getId () {
        return id;
    }

    public String getProposedExerciseId () {
        return proposedExerciseId;
    }

    public RoundsCounter getRoundsCounter () {
        return roundsCounter;
    }

    public String getComments () {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ResultExercise that = (ResultExercise)o;

        if(id != null? !id.equals(that.id) : that.id != null) return false;
        if(proposedExerciseId != null? !proposedExerciseId.equals(that.proposedExerciseId) : that.proposedExerciseId != null)
            return false;
        if(roundsCounter != null? !roundsCounter.equals(that.roundsCounter) : that.roundsCounter != null)
            return false;
        return comments != null? comments.equals(that.comments) : that.comments == null;
    }

    @Override
    public int hashCode() {
        int result = id != null? id.hashCode() : 0;
        result = 31 * result + (proposedExerciseId != null? proposedExerciseId.hashCode() : 0);
        result = 31 * result + (roundsCounter != null? roundsCounter.hashCode() : 0);
        result = 31 * result + (comments != null? comments.hashCode() : 0);
        return result;
    }
}
