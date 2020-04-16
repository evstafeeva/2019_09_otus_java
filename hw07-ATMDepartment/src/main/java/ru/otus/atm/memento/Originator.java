package ru.otus.atm.memento;

import ru.otus.atm.ATM;
import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.Denominations;

import java.util.Map;
import java.util.Stack;

public class Originator {
    private final Stack<Memento> stack = new Stack<>();

    public void saveState(ATM atm) {
        stack.push(new Memento(atm.getCells()));
    }

    public Map<Denominations, Cell> restoreState() {
        return stack.pop().getState();
    }
}