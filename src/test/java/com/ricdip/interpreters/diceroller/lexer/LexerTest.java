package com.ricdip.interpreters.diceroller.lexer;

import com.ricdip.interpreters.diceroller.token.Token;
import com.ricdip.interpreters.diceroller.token.TokenType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

class LexerTest {

    @Test
    void exceptionNextWithEmptyInput() {
        Lexer lexer = new Lexer("");

        Assertions.assertFalse(lexer.hasNext());
        Exception e = Assertions.assertThrows(NoSuchElementException.class, lexer::next);
        Assertions.assertEquals("already reached EOF", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"\n ", "\n", "   ", ""})
    void whiteSpacesInput(String whiteSpaceString) {
        Lexer lexer = new Lexer(whiteSpaceString);

        Assertions.assertFalse(lexer.hasNext());
    }

    @ParameterizedTest
    @MethodSource("provideInput")
    void validInput(String inputString, List<Token> expectedTokens) {
        Lexer lexer = new Lexer(inputString);

        List<Token> result = new ArrayList<>();
        while (lexer.hasNext()) {
            result.add(lexer.next());
        }

        Assertions.assertEquals(expectedTokens, result);
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(
                Arguments.of("1-1+2*4/6", List.of(
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.MINUS, "-"),
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.INT, "2"),
                        new Token(TokenType.ASTERISK, "*"),
                        new Token(TokenType.INT, "4"),
                        new Token(TokenType.SLASH, "/"),
                        new Token(TokenType.INT, "6")
                )),
                Arguments.of("(1+2)*4/6", List.of(
                        new Token(TokenType.LPAREN, "("),
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.INT, "2"),
                        new Token(TokenType.RPAREN, ")"),
                        new Token(TokenType.ASTERISK, "*"),
                        new Token(TokenType.INT, "4"),
                        new Token(TokenType.SLASH, "/"),
                        new Token(TokenType.INT, "6")
                )),
                Arguments.of("1+(1+2)*1d4", List.of(
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.LPAREN, "("),
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.INT, "2"),
                        new Token(TokenType.RPAREN, ")"),
                        new Token(TokenType.ASTERISK, "*"),
                        new Token(TokenType.DICE, "1d4")
                )),
                Arguments.of("2d20", List.of(
                        new Token(TokenType.DICE, "2d20")
                )),
                Arguments.of("3d8+5", List.of(
                        new Token(TokenType.DICE, "3d8"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.INT, "5")
                )),
                Arguments.of("3d8[1+2]", List.of(
                        new Token(TokenType.DICE, "3d8"),
                        new Token(TokenType.LSQUARE, "["),
                        new Token(TokenType.INT, "1"),
                        new Token(TokenType.PLUS, "+"),
                        new Token(TokenType.INT, "2"),
                        new Token(TokenType.RSQUARE, "]")
                ))
        );
    }
}