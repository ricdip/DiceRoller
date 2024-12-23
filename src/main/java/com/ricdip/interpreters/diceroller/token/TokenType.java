package com.ricdip.interpreters.diceroller.token;

/**
 * Enum that contains all possible types of token.
 */
public enum TokenType {
    PLUS, // token '+'
    MINUS, // token '-'
    ASTERISK, // token '*'
    SLASH, // token '/'
    ILLEGAL, // a not valid token
    INT, // token that contains a sequence of numbers
    DICE, // token that contains a D&D dice
    LPAREN, // token '('
    RPAREN, // token ')'
    LSQUARE, // token '['
    RSQUARE // token ']'
}
