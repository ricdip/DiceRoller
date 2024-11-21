package com.ricdip.interpreters.repl;

import com.ricdip.interpreters.lexer.Lexer;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Class that performs RLPL (Read-Lex-Print-Loop).
 */
public class RLPL {
    /**
     * Method that starts reading input text from {@link InputStream} argument and prints the
     * stream of tokens using the {@link PrintWriter} argument.
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

            // create lexer
            Lexer lexer = new Lexer(line);

            // print lexer output until EOF
            while (lexer.hasNext()) {
                out.println(lexer.next());
            }

            out.println();
            out.flush();
        }
    }
}
