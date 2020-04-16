package ru.otus.atm.cell;

import ru.otus.atm.exceptions.ImpossibleAmountException;

public class CellImpl implements Cell {

    private int count;

    public CellImpl(){
        count = 0;
    }

    public CellImpl(int count){
        this.count = count;
    }

    public CellImpl(Cell cell) {
        this.count = cell.getCount();
    }

    @Override
    public void addBanknote(){
        this.count++;
    }

    public void addBanknote(int count){
        this.count+=count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void getBanknote() throws ImpossibleAmountException {
        if(count==0)
            throw new ImpossibleAmountException();
        this.count--;
    }
}
