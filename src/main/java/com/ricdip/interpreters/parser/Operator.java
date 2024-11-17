package com.ricdip.interpreters.parser;

import com.ricdip.interpreters.symbol.Symbol;
import com.ricdip.interpreters.token.TokenType;
import lombok.Getter;

@Getter
public enum Operator {
    PLUS(Symbol.PLUS),
    MINUS(Symbol.MINUS),
    ASTERISK(Symbol.ASTERISK),
    SLASH(Symbol.SLASH),
    LSQUARE(Symbol.LSQUARE);

    private final char value;

    Operator(char value) {
        this.value = value;
    }

    public static Operator fromTokenType(TokenType tokenType) {
        return switch (tokenType) {
            case PLUS -> Operator.PLUS;
            case MINUS -> Operator.MINUS;
            case ASTERISK -> Operator.ASTERISK;
            case SLASH -> Operator.SLASH;
            case LSQUARE -> Operator.LSQUARE;
            default -> null;
        };
    }
}
