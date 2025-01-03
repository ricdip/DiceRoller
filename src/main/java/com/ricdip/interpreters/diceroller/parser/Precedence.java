package com.ricdip.interpreters.diceroller.parser;

/**
 * Enum that contains all possible precedence types.
 * the lowest precedence is 0 (LOWEST), the greatest precedence is 4 (SEPARATED_DICE_ROLL).
 */
public enum Precedence {
    LOWEST,
    SUMMATION, // + or -
    MULTIPLICATION, // * or /
    PREFIX, // -2
    SEPARATED_DICE_ROLL // 2d4[2]
}
