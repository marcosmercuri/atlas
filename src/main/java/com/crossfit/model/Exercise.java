package com.crossfit.model;

import com.google.common.collect.Range;

/**
 * Represents a possible physical exercise to be done.
 */
public class Exercise {
    private Range<Integer> maleRangeWeight;
    private Range<Integer> femaleRangeWeight;

    public Exercise (Range<Integer> maleRangeWeight, Range<Integer> femaleRangeWeight) {
        this.maleRangeWeight = maleRangeWeight;
        this.femaleRangeWeight = femaleRangeWeight;
    }
}
