package ru.otus.atm;

public class IllegalCountException extends Exception {
    public IllegalCountException(){
    }

    public IllegalCountException(String message){
        super(message);
    }
}
