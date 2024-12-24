package com.ricdip.interpreters.diceroller.token;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a single token.
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Token {
    private final TokenType tokenType;
    private final String lexeme;

    @Override
    public String toString() {
        return String.format("(%s, '%s')", tokenType, lexeme);
    }
}
