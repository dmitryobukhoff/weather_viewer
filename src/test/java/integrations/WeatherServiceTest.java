package integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.dmitryobukhoff.models.dtos.ForecastDto;
import ru.dmitryobukhoff.models.weather.Forecast;
import ru.dmitryobukhoff.services.WeatherAPIService;


import static org.junit.jupiter.api.Assertions.*;

public class WeatherServiceTest {

    @Test
    public void findWeatherByLocation() throws Exception{
        WeatherAPIService weatherService = new WeatherAPIService();
        ForecastDto forecast = weatherService.findForecastByLocation("Moscow");
        System.out.println(forecast);

    }
}
