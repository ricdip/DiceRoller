package com.ricdip.interpreters.object;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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
