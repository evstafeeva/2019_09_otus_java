package ru.otus.atm.exceptions;

public class NotSupportException extends Exception {
    public NotSupportException(){
    }

    public NotSupportException(String message){
        super(message);
    }
}
