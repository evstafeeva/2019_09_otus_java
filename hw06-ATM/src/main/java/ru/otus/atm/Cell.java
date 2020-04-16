package ru.otus.atm;

import ru.otus.atm.exceptions.ImpossibleAmountException;

public interface Cell {
    int getCount();
    void addBanknote();
    void getBanknote() throws ImpossibleAmountException;
}
