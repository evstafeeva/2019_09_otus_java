package ru.otus.atm;

import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;

import java.util.ArrayList;
import java.util.List;

public class ATMGroup implements ATM {
    private List<ATM> atms = new ArrayList<>();


    @Override
    public void putMoney(List<Denominations> money) {

    }

    @Override
    public List<Denominations> getMoney(int amount) throws IllegalAmountException, ImpossibleAmountException {
        return null;
    }

    @Override
    public int getBalance() {
        return 0;
    }
}
