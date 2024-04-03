package ru.dmitryobukhoff.models.weather.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Main {
    @JsonProperty("temp")
    private Double temperature;

    @JsonProperty("feels_like")
    private Double temperatureByFeels;

    @JsonProperty("temp_min")
    private Double minimumTemperature;

    @JsonProperty("temp_max")
    private Double maximumTemperature;

    @JsonProperty("pressure")
    private Integer pressure;

    @JsonProperty("humidity")
    private Integer humidity;

    @JsonProperty("sea_level")
    private Integer seaLevel;

    @JsonProperty("grnd_level")
    private Integer groundLevel;

}

