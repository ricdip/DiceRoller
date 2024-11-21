package com.ricdip.interpreters.symbol;

/**
 * Class that contains special characters that can be interpreted alongside numbers.
 */
public final class Symbol {
    public static final char PLUS = '+';
    public static final char MINUS = '-';
    public static final char ASTERISK = '*';
    public static final char SLASH = '/';
    public static final char DICE = 'd';
    public static final char EOF = '\0';
    public static final char LPAREN = '(';
    public static final char RPAREN = ')';
    public static final char LSQUARE = '[';
    public static final char RSQUARE = ']';

    private Symbol() {
    }
}
