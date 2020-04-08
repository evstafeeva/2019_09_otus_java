package ru.otus.atm.memento;

import ru.otus.atm.cell.Cell;
import ru.otus.atm.cell.CellImpl;
import ru.otus.atm.cell.Denominations;

import java.util.HashMap;
import java.util.Map;

public class Memento {
    private final Map<Denominations, Cell> cells;

    public Memento(Map<Denominations, Cell> cells) {
        //полное копирование
        this.cells = new HashMap<>();
        for(Map.Entry<Denominations, Cell> entry : cells.entrySet()){
            this.cells.put(entry.getKey(), new CellImpl(entry.getValue()));
        }
    }

    Map<Denominations, Cell> getState() {
        return cells;
    }
}
