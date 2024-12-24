package com.ricdip.interpreters.diceroller.evaluator;

import com.ricdip.interpreters.diceroller.evaluator.object.EvaluatedObject;
import com.ricdip.interpreters.diceroller.lexer.Lexer;
import com.ricdip.interpreters.diceroller.parser.Parser;
import com.ricdip.interpreters.diceroller.parser.ast.impl.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class EvaluatorTest {
    @ParameterizedTest
    @MethodSource("provideInput")
    void validInput(String inputString, String expectedOutput) {
        Lexer lexer = new Lexer(inputString);

        Parser parser = new Parser(lexer);

        Result parseResult = parser.parse();

        Assertions.assertNotNull(parseResult);

        // random roll returns always 1
        Evaluator evaluator = new Evaluator(diceType -> 1);

        EvaluatedObject result = evaluator.evaluate(parseResult);

        Assertions.assertEquals(
                expectedOutput.replaceAll("\\s+", ""),
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
                        1
                        """),
                Arguments.of("3d20", """
                        3
                        """),
                // infix expression
                Arguments.of("1+2", """
                        3
                        """),
                Arguments.of("1-2", """
                        -1
                        """),
                Arguments.of("3*4", """
                        12
                        """),
                Arguments.of("4/2", """
                        2
                        """),
                Arguments.of("5/2", """
                        2
                        """),
                Arguments.of("1+2d4", """
                        3
                        """),
                Arguments.of("2d4-1", """
                        1
                        """),
                Arguments.of("2d20+2d4", """
                        4
                        """),
                // prefix expression
                Arguments.of("-1", """
                        -1
                        """),
                // precedences
                Arguments.of("-1+2", """
                        1
                        """),
                Arguments.of("1+2+3", """
                        6
                        """),
                Arguments.of("1d8+2+3", """
                        6
                        """),
                Arguments.of("1+2*3", """
                        7
                        """),
                Arguments.of("1+2d8*3", """
                        7
                        """),
                Arguments.of("1+6/3", """
                        3
                        """),
                Arguments.of("1+-6/3", """
                        -1
                        """),
                // grouped expression
                Arguments.of("(1+2)+3", """
                        6
                        """),
                Arguments.of("(1d8+2)+3", """
                        6
                        """),
                Arguments.of("1+(2+3)", """
                        6
                        """),
                Arguments.of("2*(3+6)", """
                        18
                        """),
                Arguments.of("2*(2d8+3)", """
                        10
                        """),
                Arguments.of("2d4*(2d8+3)", """
                        10
                        """),
                Arguments.of("2*(-2+3)", """
                        2
                        """),
                // roll expression
                Arguments.of("2d20[2+5]", """
                        8,8
                        """)
        );
    }
}