package com.ricdip.interpreters.diceroller.parser.ast.impl;

import com.ricdip.interpreters.diceroller.parser.Operator;
import com.ricdip.interpreters.diceroller.parser.ast.Expression;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies an infix expression (an expression that has an infix operator: addition, subtraction,
 * multiplication, division).
 * Example: 2+3
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class InfixExpression implements Expression {
    private final Operator operator;
    private final Expression left;
    private final Expression right;

    @Override
    public String toString() {
        return String.format("(%s %s %s)", left.toString(), operator.getValue(), right.toString());
    }
}
