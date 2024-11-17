package com.ricdip.interpreters.symbol;

import lombok.Getter;

@Getter
public enum DiceType {
    D4("d4", 4),
    D6("d6", 6),
    D8("d8", 8),
    D10("d10", 10),
    D12("d12", 12),
    D20("d20", 20),
    D100("d100", 100);

    private final String diceName;
    private final Integer diceFaces;

    DiceType(String diceName, Integer diceFaces) {
        this.diceName = diceName;
        this.diceFaces = diceFaces;
    }
}
