package ru.otus.atm;

public class IllegalDenominationException extends Throwable {

    public IllegalDenominationException(){
    }

    public IllegalDenominationException(String message){
        super(message);
    }
}
