package com.ricdip.interpreters.object;

import com.ricdip.interpreters.symbol.DiceType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class DiceObject implements EvaluatedObject {
    private final Integer numberOfDices;
    private final DiceType diceType;
    private final List<Integer> diceRollResults;
    private final Integer result;

    @Override
    public ObjectType type() {
        return ObjectType.DICE;
    }

    @Override
    public String toString() {
        return result.toString();
    }

    public String toDetailsString() {
        return String.format(
                "[%d%s] = [%s] = %d",
                numberOfDices,
                diceType.getDiceName(),
                String.join(",", diceRollResults.stream().map(Object::toString).toList()),
                result
        );
    }
}
