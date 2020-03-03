package ru.otus.atm;

import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.CellImpl;
import ru.otus.atm.cell.Denominations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATMDepartment {
    private List<ATM> atms = new ArrayList<>();

    public ATMDepartment() {
    }

    public ATMDepartment(ATM atm) {
        addATM(atm);
    }

    public ATMDepartment(List<ATM> atms) {
        addATM(atms);
    }

    public void addATM(ATM atm) {
        atms.add(atm);
    }

    public void addATM(List<ATM> atms) {
        this.atms.addAll(atms);
    }

    public boolean removeATM(ATM atm) {
        return atms.remove(atm);
    }

    public int getBalance() {
        int balance = 0;
        for (ATM atm : atms) {
            balance += atm.getBalance();
        }
        return balance;
    }

    //возвращает суммарное количество купюр во всех атм
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

    //восстановление на одну транзакцию всех atm департамента
    //Если хотя бы один восстановился - true, иначе false
    public boolean restoreATMOneStep() {
        boolean result = false;
        for (ATM atm : atms) {
            if (restoreATMOneStep(atm) == true)
                result = true;
        }
        return result;
    }

    //восстановление на одну транзакцию атм
    //если восстановился - true, если уже в изначальном состоянии - false
    public boolean restoreATMOneStep(ATM atm) {
        return atm.restoreATM();
    }

    //восстановление к первоначальному состоянию всех atm департамента
    public boolean restoreATM() {
        for (ATM atm : atms) {
            restoreATM(atm);
        }
        return true;
    }

    //восстановление к первоначальному состоянию atm
    public boolean restoreATM(ATM atm) {
        while (atm.restoreATM() == true) ;
        return true;
    }
}
