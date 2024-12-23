package com.ricdip.interpreters.diceroller.evaluator.object;

import com.ricdip.interpreters.diceroller.symbol.DiceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Class that contains the result of a simple dice roll.
 * Example: 2d20 = [10,15] = 25
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class DiceRollObject implements EvaluatedObject {
    private final Integer numberOfDices;
    private final DiceType diceType;
    private final List<Integer> diceRollResults;
    private final Integer result;

    @Override
    public ObjectType type() {
        return ObjectType.DICE_ROLL;
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
