package com.ricdip.interpreters.diceroller.random;

import com.ricdip.interpreters.diceroller.symbol.DiceType;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class that wraps a cryptographically strong random number generator used to roll random dices.
 * The wrapped RNG uses a Java {@link SecureRandom} instance.
 */
public class SecureRandomDiceRoller implements DiceRoller {
    /**
     * Cryptographically strong random number generator (RNG).
     */
    private static final SecureRandom secureRandom;

    static {
        // constructs a secure random number generator (RNG) implementing a strong algorithm or throw a runtime exception
        try {
            secureRandom = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public int roll(DiceType diceType) {
        // nextInt method returns a random number in: [origin, bound)
        return secureRandom.nextInt(1, diceType.getDiceFaces() + 1);
    }
}
