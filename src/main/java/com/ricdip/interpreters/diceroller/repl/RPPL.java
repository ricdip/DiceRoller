package com.ricdip.interpreters.diceroller.repl;

import com.ricdip.interpreters.diceroller.ast.RootASTNode;
import com.ricdip.interpreters.diceroller.lexer.Lexer;
import com.ricdip.interpreters.diceroller.parser.Parser;
import com.ricdip.interpreters.diceroller.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that performs RPPL (Read-Parse-Print-Loop).
 */
public class RPPL {
    /**
     * Method that starts reading input text from {@link InputStream} argument and prints the
     * constructed AST (Abstract Syntax Tree) using the {@link PrintWriter} argument.
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
            RootASTNode parsed = parser.parse();

            // if errors: print them and go to next iteration
            if (!parser.getErrors().isEmpty()) {
                Utils.printList(out, parser.getErrors(), true);
                continue;
            }

            // print AST
            out.println(parsed);
            out.println();
            out.flush();
        }
    }
}
