package com.ricdip.interpreters.diceroller.parser.ast.impl;

import com.ricdip.interpreters.diceroller.parser.ast.Expression;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies an integer literal.
 * Example: 5
 */
@RequiredArgsConstructor
@Getter
public class IntegerLiteral implements Expression {
    private final Integer value;

    @Override
    public String toString() {
        return value.toString();
    }
}
