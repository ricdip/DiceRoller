package com.ricdip.interpreters.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Class that identifies a single token.
 */
@RequiredArgsConstructor
@Getter
public class Token {
    private final TokenType tokenType;
    private final String lexeme;

    @Override
    public String toString() {
        return String.format("Token {type: '%s', lexeme: '%s'}", tokenType, lexeme);
    }
}
