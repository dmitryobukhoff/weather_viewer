package ru.dmitryobukhoff.exceptions;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(String message){
        super(message);
    }
}
