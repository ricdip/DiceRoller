package com.ricdip.interpreters.utils;

import java.io.PrintWriter;
import java.util.List;

public final class Utils {
    private Utils() {
    }

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
