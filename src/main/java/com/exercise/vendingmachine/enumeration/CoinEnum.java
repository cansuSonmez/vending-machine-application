package com.exercise.vendingmachine.enumeration;

public enum CoinEnum {

    CENTS_5   (5),
    CENTS_10  (10),
    CENTS_20  (20),
    CENTS_50  (50),
    CENTS_100 (100);

    private int cents;

    CoinEnum(int cents) {
        setCents(cents);
    }

    public int getCents() {
        return cents;
    }

    public void setCents(int cents) {
        this.cents = cents;
    }

}
