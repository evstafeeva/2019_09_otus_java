package ru.otus;


import ru.otus.atm.ATM;
import ru.otus.atm.ATMImpl;
import ru.otus.atm.cell.Denominations;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATMImpl();

        atm.putMoney(Arrays.asList(
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.THOUSAND_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.TEN_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES,
                Denominations.FIVE_HUNDRED_RUBLES));

        System.out.println(atm.getBalance());

        System.out.println(atm.getMoney(1510));
        System.out.println(atm.getBalance());
        System.out.println(atm.restoreATM());
        System.out.println(atm.getBalance());
        System.out.println(atm.restoreATM());
        System.out.println(atm.getBalance());
        System.out.println(atm.restoreATM());
        System.out.println(atm.getBalance());

    }
}
