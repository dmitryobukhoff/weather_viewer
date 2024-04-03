package ru.dmitryobukhoff.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.dmitryobukhoff.exceptions.LocationNotFoundException;
import ru.dmitryobukhoff.exceptions.WeatherAPIException;
import ru.dmitryobukhoff.models.dtos.ForecastDto;
import ru.dmitryobukhoff.models.weather.Forecast;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class WeatherAPIService {
    private final String TOKEN = "a12e964fe376229740153925effe5b5c";
    private final HttpClient client = HttpClient.newHttpClient();
    private final String URL = "https://api.openweathermap.org/data/2.5/weather?appid=" + TOKEN + "&units=metric";
    private final ObjectMapper objectMapper = new ObjectMapper();
    public ForecastDto findForecastByLocation(String locationName) throws WeatherAPIException{
        try{
            return new ForecastDto(objectMapper.readValue(findWeatherByLocation(locationName), Forecast.class));
        }catch (URISyntaxException | LocationNotFoundException exception){
            throw new WeatherAPIException(exception.getMessage());
        }catch (IOException | InterruptedException exception){
            throw new WeatherAPIException("You have not connection! Use VPN services");
        }

    }
    public ForecastDto findForecastByCoordinates(Double latitude, Double longitude) throws WeatherAPIException{
        try{
            return new ForecastDto(objectMapper.readValue(findWeatherByCoordinates(latitude, longitude), Forecast.class));
        }catch (URISyntaxException | LocationNotFoundException exception){
            throw new WeatherAPIException(exception.getMessage());
        }catch ( IOException | InterruptedException exception){
            throw new WeatherAPIException("You have not connection! Use VPN services");
        }
    }
    private String findWeatherByLocation(String cityName) throws URISyntaxException, IOException, InterruptedException{
        String url = URL + "&q=" + cityName;
        return makeRequest(url);
    }
    private String findWeatherByCoordinates(Double latitude, Double longitude) throws URISyntaxException, IOException, InterruptedException {
        String url = URL + "&lat=" + latitude.toString() +  "&lon=" + longitude.toString();
        return makeRequest(url);
    }

    private String makeRequest(String url) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI(url))
                .timeout(Duration.ofSeconds(5))
                .GET()
                .build();
        HttpResponse<String> httpResponse = client.send(httpRequest,  HttpResponse.BodyHandlers.ofString());
        if(httpResponse.statusCode() == HttpServletResponse.SC_NOT_FOUND) throw new LocationNotFoundException("Location not found");
        return httpResponse.body();
    }
}
