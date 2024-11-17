package com.ricdip.interpreters.parser;

public enum Precedence {
    LOWEST,
    SUM, // + or -
    PRODUCT, // * or /
    PREFIX, // -X
    ROLL // 2d4[2]
}
