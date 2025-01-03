package com.ricdip.interpreters.diceroller.evaluator.object;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Class that contains an evaluated integer.
 */
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class IntegerObject implements EvaluatedObject {
    private final Integer value;

    @Override
    public ObjectType type() {
        return ObjectType.INT;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
