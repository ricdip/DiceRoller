package com.ricdip.interpreters.random;

import com.ricdip.interpreters.symbol.DiceType;

/**
 * Interface that must be implemented by all the used random dice rollers.
 */
public interface DiceRoller {
    /**
     * Methods that returns a random dice result that depends on the dice type.
     * The result is between 1 and the max number that can be rolled with a specific dice type
     * [1, dice_max_number].
     *
     * @param diceType The dice type to roll.
     * @return The roll result [1, dice_max_number].
     */
    int roll(DiceType diceType);
}
