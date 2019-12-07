package ru.otus.atm.exceptions;

public class ImpossibleAmountException extends Exception {
    public ImpossibleAmountException(){
    }

    public ImpossibleAmountException(String message){
        super(message);
    }
}
