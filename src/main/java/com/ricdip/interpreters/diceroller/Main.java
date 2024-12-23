package com.ricdip.interpreters.diceroller;


import com.ricdip.interpreters.diceroller.cli.CLIApplication;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIApplication()).execute(args);
        System.exit(exitCode);
    }
}
