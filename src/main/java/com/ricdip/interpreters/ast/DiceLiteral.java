package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.symbol.DiceType;
import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a single dice literal.
 * Example: 2d20
 */
@RequiredArgsConstructor
@Getter
public class DiceLiteral implements Expression {
    private final Integer numberOfDices;
    private final DiceType diceType;

    @Override
    public TokenType getTokenType() {
        return TokenType.DICE;
    }

    @Override
    public String toString() {
        return String.format("%d%s", numberOfDices, diceType.getDiceName());
    }
}
