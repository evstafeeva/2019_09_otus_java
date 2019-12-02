package ru.otus.atm;

public class IllegalAmountException extends Exception {
    public IllegalAmountException(){
    }

    public IllegalAmountException(String message){
        super(message);
    }
}
