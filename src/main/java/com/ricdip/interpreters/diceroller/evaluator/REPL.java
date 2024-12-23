package com.ricdip.interpreters.diceroller.evaluator;

import com.ricdip.interpreters.diceroller.Constants;
import com.ricdip.interpreters.diceroller.evaluator.object.EvaluatedObject;
import com.ricdip.interpreters.diceroller.lexer.Lexer;
import com.ricdip.interpreters.diceroller.parser.Parser;
import com.ricdip.interpreters.diceroller.parser.ast.impl.Result;
import com.ricdip.interpreters.diceroller.random.DiceRoller;
import com.ricdip.interpreters.diceroller.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that performs REPL (Read-Evaluate-Print-Loop).
 */
@RequiredArgsConstructor
public class REPL {
    private final DiceRoller diceRoller;

    /**
     * Method that starts reading input text from {@link InputStream} argument and prints the
     * evaluated parsed nodes using the {@link PrintWriter} argument.
     *
     * @param in  The {@link InputStream} used to read input.
     * @param out The {@link PrintWriter} used to print the output tokens.
     */
    public void start(InputStream in, PrintWriter out) {
        Scanner scanner = new Scanner(in);

        while (true) {
            out.print(Constants.PROMPT);
            out.flush();

            // wait for input from user
            String line = scanner.nextLine();
            if (Constants.QUIT.equals(line) || StringUtils.isBlank(line)) {
                break;
            }

            // create lexer and parser
            Lexer lexer = new Lexer(line);
            Parser parser = new Parser(lexer);

            // parse input
            Result parsed = parser.parse();

            // if errors: print them and go to next iteration
            if (!parser.getErrors().isEmpty()) {
                Utils.printList(out, parser.getErrors(), true);
                continue;
            }

            Evaluator evaluator = new Evaluator(diceRoller);

            // print AST
            out.println("\t" + parsed.toString());
            out.flush();

            // evaluate parsed line
            EvaluatedObject evaluated = evaluator.evaluate(parsed);

            // print evaluator output
            Utils.printList(out, evaluator.getPrints(), false);

            out.println(evaluated);
            out.println();
            out.flush();
        }
    }
}
