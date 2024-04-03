package ru.dmitryobukhoff.exceptions;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException(String message){
        super(message);
    }
}
