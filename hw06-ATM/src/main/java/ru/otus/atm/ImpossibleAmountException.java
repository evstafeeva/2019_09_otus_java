package ru.otus.atm;

public class ImpossibleAmountException extends Exception {
    public ImpossibleAmountException(){
    }

    public ImpossibleAmountException(String message){
        super(message);
    }
}
