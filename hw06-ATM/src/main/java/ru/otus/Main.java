package ru.otus;

import ru.otus.atm.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws IllegalDenominationException, IllegalCountException, IllegalAmountException, ImpossibleAmountException {
        ATM atm = new ATM();

        atm.putMoney(500, 5);
        atm.putMoney(10, 12);
        atm.putMoney(1000, 3);

        System.out.println(atm.getBalance());

        List<Bundle> myMoney = atm.getMoney(1610);
        System.out.println(myMoney);
        System.out.println(atm.getBalance());
    }
}
