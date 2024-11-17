package com.ricdip.interpreters.ast;

import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
