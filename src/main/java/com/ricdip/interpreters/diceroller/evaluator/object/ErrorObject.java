package com.ricdip.interpreters.diceroller.evaluator.object;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Class that contains an error occurred while evaluating.
 */
@Getter
@EqualsAndHashCode
public class ErrorObject implements EvaluatedObject {
    private final String message;

    public ErrorObject(String message) {
        this.message = message;
    }

    public ErrorObject(String message, Object... args) {
        this.message = String.format(message, args);
    }

    @Override
    public ObjectType type() {
        return ObjectType.ERROR;
    }

    @Override
    public String toString() {
        return String.format("evaluation error: %s", message);
    }
}
