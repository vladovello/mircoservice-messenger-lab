package com.messenger.sharedlib.parameter.exception;

public class IntervalRangeViolationException extends IntervalException {
    public IntervalRangeViolationException() {
    }

    public IntervalRangeViolationException(String leftBound, String rightBound) {
        super(String.format("Invalid interval [%s..%s]", leftBound, rightBound));
    }
}
