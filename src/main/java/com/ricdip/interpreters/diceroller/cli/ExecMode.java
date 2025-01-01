package com.ricdip.interpreters.diceroller.cli;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import picocli.CommandLine.Option;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecMode {
    @Option(names = {"-ml", "--mode-lexer"}, description = "Enable lexer mode.")
    private boolean rlpl;
    @Option(names = {"-mp", "--mode-parser"}, description = "Enable parser mode.")
    private boolean rppl;
    @Option(names = {"-me", "--mode-evaluator"}, description = "Enable evaluator mode [default].")
    private boolean repl;

    public ExecModeTypes getExecMode() {
        if (rlpl) {
            return ExecModeTypes.LEXER;
        } else if (rppl) {
            return ExecModeTypes.PARSER;
        } else if (repl) {
            return ExecModeTypes.EVALUATOR;
        } else {
            return ExecModeTypes.EVALUATOR;
        }
    }
}
