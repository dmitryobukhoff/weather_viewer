package ru.dmitryobukhoff.exceptions;

public class NonExistentLoginException extends RuntimeException{
    public NonExistentLoginException(String message){
        super(message);
    }
}
