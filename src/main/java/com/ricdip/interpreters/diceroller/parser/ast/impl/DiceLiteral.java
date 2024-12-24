package com.ricdip.interpreters.diceroller.parser.ast.impl;

import com.ricdip.interpreters.diceroller.parser.ast.Expression;
import com.ricdip.interpreters.diceroller.symbol.DiceType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a single dice literal.
 * Example: 2d20
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class DiceLiteral implements Expression {
    private final Integer numberOfDices;
    private final DiceType diceType;

    @Override
    public String toString() {
        return String.format("%d%s", numberOfDices, diceType.getDiceName());
    }
}
