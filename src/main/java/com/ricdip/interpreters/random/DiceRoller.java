package com.ricdip.interpreters.random;

import com.ricdip.interpreters.symbol.DiceType;

public interface DiceRoller {
    int roll(DiceType diceType);
}
