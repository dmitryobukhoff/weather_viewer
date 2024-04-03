package ru.dmitryobukhoff.models.weather.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Weather {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("main")
    private String state;

    @JsonProperty("description")
    private String description;

    @JsonProperty("icon")
    private String icon;
}
