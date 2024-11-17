package com.ricdip.interpreters.cli;

import com.ricdip.interpreters.random.DiceRoller;
import com.ricdip.interpreters.random.SecureRandomDiceRoller;
import com.ricdip.interpreters.repl.REPL;
import com.ricdip.interpreters.repl.RLPL;
import com.ricdip.interpreters.repl.RPPL;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

import java.io.PrintWriter;

@Command(
        name = "<jar file name>",
        versionProvider = BuildInfoVersionProvider.class,
        mixinStandardHelpOptions = true,
        description = "A D&D dice roller written in Java"
)
public class CLIApplication implements Runnable {
    @ArgGroup
    private final ExecMode execMode = new ExecMode();

    @Override
    public void run() {
        switch (execMode.getExecMode()) {
            case LEXER: {
                RLPL rlpl = new RLPL();
                rlpl.start(System.in, new PrintWriter(System.out));
                break;
            }
            case PARSER: {
                RPPL rppl = new RPPL();
                rppl.start(System.in, new PrintWriter(System.out));
                break;
            }
            case EVALUATOR: {
                DiceRoller diceRoller = new SecureRandomDiceRoller();
                REPL repl = new REPL(diceRoller);
                repl.start(System.in, new PrintWriter(System.out));
                break;
            }
        }
    }
}
