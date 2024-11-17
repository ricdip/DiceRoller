package com.ricdip.interpreters.random;

import com.ricdip.interpreters.symbol.DiceType;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecureRandomDiceRoller implements DiceRoller {
    // cryptographically strong random number generator (RNG)
    private static final SecureRandom secureRandom;

    static {
        // constructs a secure random number generator (RNG) implementing a strong algorithm
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int roll(DiceType diceType) {
        // random number in [origin, bound)
        return secureRandom.nextInt(1, diceType.getDiceFaces() + 1);
    }
}
