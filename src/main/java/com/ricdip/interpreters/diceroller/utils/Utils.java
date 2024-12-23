package com.ricdip.interpreters.diceroller.utils;

import java.io.PrintWriter;
import java.util.List;

/**
 * Class that contains some useful functions.
 */
public final class Utils {
    private Utils() {
    }

    /**
     * Prints the content of a list of strings using the specified {@link PrintWriter}.
     *
     * @param out     The {@link PrintWriter} used to print the list.
     * @param list    The list of string to print.
     * @param newLine True prints a newline after printing all the list contents.
     */
    public static void printList(PrintWriter out, List<String> list, boolean newLine) {
        for (String element : list) {
            out.println(String.format("\t%s", element));
        }

        if (newLine) {
            out.println();
        }

        out.flush();
    }
}
