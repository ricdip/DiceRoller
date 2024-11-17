package com.ricdip.interpreters;


import com.ricdip.interpreters.cli.CLIApplication;
import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CLIApplication()).execute(args);
        System.exit(exitCode);
    }
}
