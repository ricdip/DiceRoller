package com.ricdip.interpreters.repl;

import com.ricdip.interpreters.ast.RootASTNode;
import com.ricdip.interpreters.lexer.Lexer;
import com.ricdip.interpreters.parser.Parser;
import com.ricdip.interpreters.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class RPPL {
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
