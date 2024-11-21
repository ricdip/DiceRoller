package com.ricdip.interpreters.parser;

/**
 * Enum that contains all possible precedence types.
 * the lowest precedence is 0 (LOWEST), the greatest precedence is 4 (SEPARATED_DICE_ROLL).
 */
public enum Precedence {
    LOWEST,
    SUM, // + or -
    PRODUCT, // * or /
    PREFIX, // -2
    SEPARATED_DICE_ROLL // 2d4[2]
}
