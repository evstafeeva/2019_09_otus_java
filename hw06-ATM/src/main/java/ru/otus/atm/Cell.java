package ru.otus.atm;

class Cell {

    private int count;

    Cell(){
        count = 0;
    }

    Cell(int count){
        this.count = count;
    }

    void addBanknote(Integer num) {
        this.count+=num;
    }

    int getCount() {
        return count;
    }

    //извлечь i банкнот из ячейки
    void getBanknote(int i){
        this.count-=i;
    }

}
