package com.ricdip.interpreters.diceroller.ast;

import com.ricdip.interpreters.diceroller.parser.Operator;
import com.ricdip.interpreters.diceroller.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a prefix expression (an expression that has a prefix operator: minus).
 * Example: -2
 */
@RequiredArgsConstructor
@Getter
public class PrefixExpression implements Expression {
    private final TokenType tokenType;
    private final Operator operator;
    private final Expression right;

    @Override
    public String toString() {
        return String.format("(%s%s)", operator.getValue(), right.toString());
    }
}