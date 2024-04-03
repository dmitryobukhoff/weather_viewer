package ru.dmitryobukhoff.models.dtos;

import lombok.Data;
import ru.dmitryobukhoff.models.weather.Forecast;
import ru.dmitryobukhoff.models.weather.json.Coordinates;
import ru.dmitryobukhoff.models.weather.json.Main;
import ru.dmitryobukhoff.models.weather.json.System;
import ru.dmitryobukhoff.models.weather.json.Weather;
import ru.dmitryobukhoff.models.weather.json.Wind;

import java.util.Arrays;

@Data
public class ForecastDto {
    private Coordinates coordinates;
    private Weather[] weather;
    private String name;
    private Main main;
    private Wind wind;
    private System system;
    public ForecastDto(Forecast forecast){
        this.coordinates = forecast.getCoordinates();
        this.weather = forecast.getWeather();
        this.name = forecast.getName();
        this.main = forecast.getMain();
        this.wind = forecast.getWind();
        this.system = forecast.getSys();
    }
    @Override
    public String toString() {
        return "ForecastDto{" +
                "coordinates=" + coordinates +
                ", weather=" + Arrays.toString(weather) +
                ", name='" + name + '\'' +
                ", main=" + main +
                '}';
    }
}
