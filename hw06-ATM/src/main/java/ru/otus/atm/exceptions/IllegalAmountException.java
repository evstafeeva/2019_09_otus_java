package ru.otus.atm.exceptions;

public class IllegalAmountException extends Exception {
    public IllegalAmountException(){
    }

    public IllegalAmountException(String message){
        super(message);
    }
}
