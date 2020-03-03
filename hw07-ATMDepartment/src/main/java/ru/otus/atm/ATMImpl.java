package ru.otus.atm;


import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.CellImpl;
import ru.otus.atm.cell.Denominations;
import ru.otus.atm.exceptions.IllegalAmountException;
import ru.otus.atm.exceptions.ImpossibleAmountException;
import ru.otus.atm.memento.Originator;

import java.util.*;

public class ATMImpl implements ATM {

    //private int id;

    private Map<Denominations, Cell> cells;

    //для хранения предыдущих состояний
    private Originator originator = new Originator();

    //в конструкторе банкомата создадим для каждого элемента enum-а свою ячейку.
    public ATMImpl() {
        cells = new HashMap<>();
        for (Denominations denomination : Denominations.values()) {
            cells.put(denomination, new CellImpl());
        }
    }

    //конструктор банкомата с некоторым начальным состоянием
    public ATMImpl(Map<Denominations, Cell> cells) {
        this.cells = new HashMap<>(cells);
    }

    public ATMImpl(List<Denominations> money){
        this();
        for (Denominations banknote : money) {
            cells.get(banknote).addBanknote();
        }
    }

    @Override
    public void putMoney(List<Denominations> money) {
        //сохраняем состояние
        originator.saveState(this);
        //сортируем по ячейкам
        for (Denominations banknote : money) {
            cells.get(banknote).addBanknote();
        }
    }

    @Override
    public List<Denominations> getMoney(int amount) throws IllegalAmountException, ImpossibleAmountException {
        if (amount <= 0)
            throw new IllegalAmountException();
        List<Denominations> combinationOfBanknote = new ArrayList<>();
        //сначала проверяем на возможность снять такую сумму в принципе
        if (!checkOpportunity(amount, combinationOfBanknote))
            throw new ImpossibleAmountException();
        //если возможность есть,сохраняем состояние, забираем купюры из ячеек
        originator.saveState(this);
        getMoneyFromCells(combinationOfBanknote);
        return combinationOfBanknote;
    }

    @Override
    public int getBalance() {
        int sum = 0;
        for (Denominations denomination : Denominations.values()) {
            sum += cells.get(denomination).getCount() * denomination.getValue();
        }
        return sum;
    }

    public Map<Denominations, Cell> getCells() {
        return cells;
    }

    public boolean restoreATM() {
        try {
            cells = originator.restoreState();
            return true;
        }catch(EmptyStackException e){
            return false;
        }
    }

    @Override
    public boolean isGroup() {
        return false;
    }

    private void getMoneyFromCells(List<Denominations> combinationOfBanknote) throws ImpossibleAmountException {
        for (Denominations banknote : combinationOfBanknote) {
            cells.get(banknote).getBanknote();
        }
    }

    private boolean checkOpportunity(int amount, List<Denominations> combinationOfBanknote) {
        for (Denominations denomination : Denominations.values()) {
            if (cells.get(denomination).getCount() == 0 || amount / denomination.getValue() <= 0)
                continue;
            if (amount / denomination.getValue() >= cells.get(denomination).getCount()) {
                for (int i = 0; i < cells.get(denomination).getCount(); i++) {
                    combinationOfBanknote.add(denomination);
                }
                amount -= denomination.getValue() * (cells.get(denomination).getCount());
            } else {
                for (int i = 0; i < amount / denomination.getValue(); i++) {
                    combinationOfBanknote.add(denomination);
                }
                amount -= denomination.getValue() * (amount / denomination.getValue());
            }
        }
        if (amount != 0)
            return false;
        return true;
    }

}
