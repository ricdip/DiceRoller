package com.ricdip.interpreters.object;

import com.ricdip.interpreters.symbol.DiceType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class DiceRollObject implements EvaluatedObject {
    private final Integer numberOfDices;
    private final DiceType diceType;
    private final List<Integer> diceRollResults;
    private final Integer expressionResult;
    private final List<Integer> results;

    @Override
    public ObjectType type() {
        return ObjectType.ROLL;
    }

    @Override
    public String toString() {
        return String.format(
                "%s",
                String.join(",", results.stream().map(Object::toString).toList())
        );
    }

    public String toDetailsString() {
        return String.format(
                "[%d%s] = [%s] + [%d] = %s",
                numberOfDices,
                diceType.getDiceName(),
                String.join(",", diceRollResults.stream().map(Object::toString).toList()),
                expressionResult,
                String.join(",", results.stream().map(Object::toString).toList())
        );
    }
}
