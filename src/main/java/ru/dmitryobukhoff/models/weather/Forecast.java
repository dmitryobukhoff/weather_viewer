package ru.dmitryobukhoff.models.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.dmitryobukhoff.models.weather.json.*;
import ru.dmitryobukhoff.models.weather.json.System;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {
    @JsonProperty("coord")
    private Coordinates coordinates;
    @JsonProperty("weather")
    private Weather[] weather;
    @JsonProperty("base")
    private String base;
    @JsonProperty("main")
    private Main main;
    @JsonProperty("visibility")
    private Double visibility;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("clouds")
    private Clouds clouds;
    @JsonProperty("dt")
    private Long dt;
    @JsonProperty("sys")
    private System sys;
    @JsonProperty("timezone")
    private Long timezone;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("cod")
    private Integer cod;
}
