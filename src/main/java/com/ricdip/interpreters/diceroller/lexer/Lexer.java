package com.ricdip.interpreters.diceroller.lexer;

import com.ricdip.interpreters.diceroller.symbol.Symbol;
import com.ricdip.interpreters.diceroller.token.Token;
import com.ricdip.interpreters.diceroller.token.TokenType;
import lombok.NonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that perform lexical analysis on the input, converting a stream of text into a stream of
 * tokens.
 */
public class Lexer implements Iterator<Token> {
    private final String input;
    private Integer currPosition;
    private Integer peekPosition;
    private Character currChar;

    /**
     * Constructs a {@link Lexer} instance used to perform lexical analysis on the input string.
     *
     * @param input The input string that contains the characters to tokenize.
     */
    public Lexer(@NonNull String input) {
        this.input = input.strip();
        currPosition = null;
        peekPosition = 0;
    }

    private void readChar() {
        if (peekPosition < input.length()) {
            currPosition = peekPosition;
            peekPosition += 1;
            currChar = input.charAt(currPosition);
            if (Character.isWhitespace(currChar)) {
                readChar();
            }
        }
    }

    private Character peekChar() {
        if (peekPosition >= input.length()) {
            return Symbol.EOF;
        } else {
            return input.charAt(peekPosition);
        }
    }

    /**
     * Check if there are characters left to tokenize from the input.
     *
     * @return True if there are characters to tokenize, False otherwise.
     */
    @Override
    public boolean hasNext() {
        return Symbol.EOF != peekChar();
    }

    /**
     * Returns the next {@link Token} converted from the input stream.
     *
     * @return The next token converted from the input stream.
     * @throws NoSuchElementException If this method is called after the last token has been returned.
     */
    @Override
    public Token next() {
        if (hasNext()) {
            readChar();
            Token currToken = null;

            switch (currChar) {
                case Symbol.PLUS:
                    currToken = new Token(TokenType.PLUS, currChar.toString());
                    break;
                case Symbol.MINUS:
                    currToken = new Token(TokenType.MINUS, currChar.toString());
                    break;
                case Symbol.ASTERISK:
                    currToken = new Token(TokenType.ASTERISK, currChar.toString());
                    break;
                case Symbol.SLASH:
                    currToken = new Token(TokenType.SLASH, currChar.toString());
                    break;
                case Symbol.LPAREN:
                    currToken = new Token(TokenType.LPAREN, currChar.toString());
                    break;
                case Symbol.RPAREN:
                    currToken = new Token(TokenType.RPAREN, currChar.toString());
                    break;
                case Symbol.LSQUARE:
                    currToken = new Token(TokenType.LSQUARE, currChar.toString());
                    break;
                case Symbol.RSQUARE:
                    currToken = new Token(TokenType.RSQUARE, currChar.toString());
                    break;
                default:
                    if (Character.isDigit(currChar)) {
                        currToken = createIntOrDiceToken();
                    } else {
                        currToken = new Token(TokenType.ILLEGAL, currChar.toString());
                    }
            }

            return currToken;
        } else {
            throw new NoSuchElementException("already reached EOF");
        }
    }

    private Token createIntOrDiceToken() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append(currChar);

        resultBuilder.append(nextNumber());

        if (Symbol.DICE == peekChar()) {
            readChar();
            resultBuilder.append(currChar);

            resultBuilder.append(nextNumber());
        }

        String result = resultBuilder.toString();

        if (result.endsWith(String.valueOf(Symbol.DICE))) {
            return new Token(TokenType.ILLEGAL, currChar.toString());
        }

        if (result.contains(String.valueOf(Symbol.DICE))) {
            return new Token(TokenType.DICE, result);
        } else {
            return new Token(TokenType.INT, result);
        }
    }

    private String nextNumber() {
        StringBuilder resultBuilder = new StringBuilder();

        while (Character.isDigit(peekChar())) {
            readChar();
            resultBuilder.append(currChar);
        }

        return resultBuilder.toString();
    }
}
