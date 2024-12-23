package com.ricdip.interpreters.diceroller.ast;

import com.ricdip.interpreters.diceroller.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a roll expression (an expression that sums the result of each rolled dice with the result of
 * an expression).
 * Example: 2d4[2+3] = [1,3] + [5] = 6,8.
 */
@RequiredArgsConstructor
@Getter
public class RollExpression implements Expression {
    private final DiceLiteral dice;
    private final Expression expression;

    @Override
    public TokenType getTokenType() {
        return TokenType.LSQUARE;
    }

    @Override
    public String toString() {
        return String.format("%s[%s]", dice.toString(), expression.toString());
    }
}
