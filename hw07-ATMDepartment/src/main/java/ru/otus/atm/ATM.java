package ru.otus.atm;

import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.Denominations;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;
import ru.otus.atm.exceptions.NotSupportException;

import java.util.List;
import java.util.Map;

public interface ATM {
    void putMoney(List<Denominations> money) throws Exception;
    List<Denominations> getMoney(int amount) throws Exception;
    int getBalance();
    public Map<Denominations, Cell> getCells();
    public boolean restoreATM();
    public boolean isGroup();
}
