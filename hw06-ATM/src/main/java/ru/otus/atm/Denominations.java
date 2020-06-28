package ru.otus.atm;

public enum Denominations {
    FIVE_THOUSAND_RUBLES(5000),
    THOUSAND_RUBLES(1000),
    FIVE_HUNDRED_RUBLES(500),
    HUNDRED_RUBLES(100),
    FIFTY_RUBLES(50),
    TEN_RUBLES(10);

    private int denomination;

    Denominations(int i) {
        denomination = i;
    }
    public int getValue() {
        return denomination;
    }
}
