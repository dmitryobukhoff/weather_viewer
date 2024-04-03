package ru.dmitryobukhoff.models.weather.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;


@Data
public class System {
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("country")
    private String country;
    @JsonProperty("sunrise")
    private Long sunrise;
    @JsonProperty("sunset")
    private Long sunset;

    public Date getSunriseTime(){
        return new Date(sunrise*1000L);
    }
    public Date getSunsetTime(){
        return new Date(sunset*1000L);
    }
}
