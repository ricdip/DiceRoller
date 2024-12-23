package com.ricdip.interpreters.diceroller.parser;

import com.ricdip.interpreters.diceroller.symbol.Symbol;
import com.ricdip.interpreters.diceroller.token.TokenType;
import lombok.Getter;

/**
 * Enum that contains all possible parsable operators.
 */
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

    /**
     * Returns the correct {@link Operator} associated with a {@link TokenType} object.
     *
     * @param tokenType The {@link TokenType} object to be used.
     * @return The correct {@link Operator} associated with a {@link TokenType} object.
     */
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
