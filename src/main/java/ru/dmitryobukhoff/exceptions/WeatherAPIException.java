package ru.dmitryobukhoff.exceptions;

public class WeatherAPIException extends RuntimeException{
    public WeatherAPIException(String message){
        super(message);
    }
}
