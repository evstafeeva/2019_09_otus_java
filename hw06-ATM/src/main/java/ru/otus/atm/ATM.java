package ru.otus.atm;

import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;

import java.util.List;
import java.util.Map;

public interface ATM {
    void putMoney(List<Denominations> money);
    List<Denominations> getMoney(int amount) throws IllegalAmountException, ImpossibleAmountException;
    int getBalance();
}
