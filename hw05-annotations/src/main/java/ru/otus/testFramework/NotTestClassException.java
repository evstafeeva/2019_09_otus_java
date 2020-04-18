package ru.otus.testFramework;

public class NotTestClassException extends Exception {
    public NotTestClassException(){
    }

    public NotTestClassException(String message){
        super(message);
    }
}