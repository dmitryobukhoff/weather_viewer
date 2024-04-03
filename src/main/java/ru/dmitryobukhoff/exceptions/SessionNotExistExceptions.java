package ru.dmitryobukhoff.exceptions;

public class SessionNotExistExceptions extends RuntimeException{
    public SessionNotExistExceptions(String message){
        super(message);
    }
}
