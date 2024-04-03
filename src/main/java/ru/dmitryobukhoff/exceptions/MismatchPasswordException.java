package ru.dmitryobukhoff.exceptions;

public class MismatchPasswordException extends RuntimeException{
    public MismatchPasswordException(String message){
        super(message);
    }
}
