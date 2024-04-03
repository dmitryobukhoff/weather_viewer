package ru.dmitryobukhoff.models.weather.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Clouds {
    @JsonProperty("all")
    private Integer cloudyPercent;
}
