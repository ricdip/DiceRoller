package com.ricdip.interpreters.diceroller.ast;

import com.ricdip.interpreters.diceroller.token.TokenType;
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
    public TokenType getTokenType() {
        return TokenType.INT;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
