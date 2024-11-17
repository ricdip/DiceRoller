package com.ricdip.interpreters.repl;

import com.ricdip.interpreters.lexer.Lexer;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class RLPL {
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
