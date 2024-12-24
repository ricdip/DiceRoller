package com.ricdip.interpreters.diceroller.parser;

import com.ricdip.interpreters.diceroller.lexer.Lexer;
import com.ricdip.interpreters.diceroller.parser.ast.impl.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ParserTest {
    @ParameterizedTest
    @MethodSource("provideInput")
    void validInput(String inputString, String expectedASTString) {
        Lexer lexer = new Lexer(inputString);

        Parser parser = new Parser(lexer);

        Result result = parser.parse();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(
                expectedASTString.replaceAll("\\s+", ""),
                result.toString().replaceAll("\\s+", "")
        );
    }

    private static Stream<Arguments> provideInput() {
        return Stream.of(
                // integer literal
                Arguments.of("1", """
                        1
                        """),
                Arguments.of("1250", """
                        1250
                        """),
                // dice literal
                Arguments.of("1d4", """
                        1d4
                        """),
                Arguments.of("3d20", """
                        3d20
                        """),
                // infix expression
                Arguments.of("1+2", """
                        (1 + 2)
                        """),
                Arguments.of("1-2", """
                        (1 - 2)
                        """),
                Arguments.of("1*2", """
                        (1 * 2)
                        """),
                Arguments.of("1/2", """
                        (1 / 2)
                        """),
                Arguments.of("1+2d4", """
                        (1 + 2d4)
                        """),
                Arguments.of("2d4-1", """
                        (2d4 - 1)
                        """),
                Arguments.of("2d20+2d4", """
                        (2d20 + 2d4)
                        """),
                // prefix expression
                Arguments.of("-1", """
                        (-1)
                        """),
                Arguments.of("-2d4", """
                        (-2d4)
                        """),
                // precedences
                Arguments.of("-1+2", """
                        ((-1) + 2)
                        """),
                Arguments.of("1+2+3", """
                        ((1 + 2) + 3)
                        """),
                Arguments.of("1d8+2+3", """
                        ((1d8 + 2) + 3)
                        """),
                Arguments.of("1+2*3", """
                        (1 + (2 * 3))
                        """),
                Arguments.of("1+2d8*3", """
                        (1 + (2d8 * 3))
                        """),
                Arguments.of("1+2/3", """
                        (1 + (2 / 3))
                        """),
                Arguments.of("1+-2/3", """
                        (1 + ((-2) / 3))
                        """),
                // grouped expression
                Arguments.of("(1+2)+3", """
                        ((1 + 2) + 3)
                        """),
                Arguments.of("(1d8+2)+3", """
                        ((1d8 + 2) + 3)
                        """),
                Arguments.of("1+(2+3)", """
                        (1 + (2 + 3))
                        """),
                Arguments.of("1*(2+3)", """
                        (1 * (2 + 3))
                        """),
                Arguments.of("1*(2d8+3)", """
                        (1 * (2d8 + 3))
                        """),
                Arguments.of("1d4*(2d8+3)", """
                        (1d4 * (2d8 + 3))
                        """),
                Arguments.of("1*(-2+3)", """
                        (1 * ((-2) + 3))
                        """),
                // roll expression
                Arguments.of("2d20[2+5]", """
                        2d20[(2 + 5)]
                        """)
        );
    }
}