package ru.otus.atm;

import ru.otus.atm.exceptions.ImpossibleAmountException;

class CellImpl implements Cell {

    private int count;

    CellImpl(){
        count = 0;
    }

    CellImpl(int count){
        this.count = count;
    }

    @Override
    public void addBanknote(){
        this.count++;
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
