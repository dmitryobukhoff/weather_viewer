package ru.dmitryobukhoff.models.weather.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Coordinates {
    @JsonProperty("lon")
    private Double longitude;
    @JsonProperty("lat")
    private Double latitude;
}
