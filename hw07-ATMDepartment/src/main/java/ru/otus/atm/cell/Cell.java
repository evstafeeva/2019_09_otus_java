package ru.otus.atm.cell;

import ru.otus.atm.exceptions.ImpossibleAmountException;

public interface Cell {
    int getCount();
    void addBanknote();
    void addBanknote(int count);
    void getBanknote() throws ImpossibleAmountException;
}
