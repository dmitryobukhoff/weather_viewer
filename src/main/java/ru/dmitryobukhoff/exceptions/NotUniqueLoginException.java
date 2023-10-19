package ru.dmitryobukhoff.exceptions;

public class NotUniqueLoginException extends RuntimeException{
    public NotUniqueLoginException(String message) {
        super(message);
    }
}
