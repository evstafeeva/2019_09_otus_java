package ru.otus.atm;

import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.CellImpl;
import ru.otus.atm.cell.Denominations;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;
import ru.otus.atm.exceptions.NotSupportException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMGroup implements ATM {
    private List<ATM> atms = new ArrayList<>();

    @Override
    public void putMoney(List<Denominations> money) throws NotSupportException {
        throw new NotSupportException("Данная операция не поддерживается группой банкоматов!");
    }

    @Override
    public List<Denominations> getMoney(int amount) throws NotSupportException {
        throw new NotSupportException("Данная операция не поддерживается группой банкоматов!");
    }

    //возвращает суммарное количество купюр во всех атм
    @Override
    public Map<Denominations, Cell> getCells() {
        Map<Denominations, Cell> allCells = new HashMap<>();
        for (Denominations denomination : Denominations.values()) {
            allCells.put(denomination, new CellImpl());
        }
        for (ATM atm : atms) {
            Map<Denominations, Cell> atmCell = atm.getCells();
            for (Denominations denomination : Denominations.values()) {
                allCells.get(denomination).addBanknote(atmCell.get(denomination).getCount());
            }
        }
        return allCells;
    }

    //собирает сумму баланса со всех атм
    @Override
    public int getBalance() {
        int balance = 0;
        for (ATM atm : atms) {
            balance += atm.getBalance();
        }
        return balance;
    }

    //true-если хотя бы один атм обновился, иначе false
    @Override
    public boolean restoreATM() {
        boolean result = false;
        for (ATM atm : atms) {
            if (atm.restoreATM() == true)
                result = true;
        }
        return result;
    }

    @Override
    public boolean isGroup() {
        return true;
    }

    public void addATM(ATM atm){
        atms.add(atm);
    }

    public boolean removeAtm(ATM atm) {
        return atms.remove(atm);
    }

    public List<ATM> getAtms() {
        return atms;
    }
}
