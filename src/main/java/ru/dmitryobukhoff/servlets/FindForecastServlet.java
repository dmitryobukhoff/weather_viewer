package ru.dmitryobukhoff.servlets;

import ru.dmitryobukhoff.exceptions.SessionNotExistExceptions;
import ru.dmitryobukhoff.exceptions.WeatherAPIException;
import ru.dmitryobukhoff.models.Location;
import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.models.dtos.ForecastDto;
import ru.dmitryobukhoff.repositories.SessionRepository;
import ru.dmitryobukhoff.services.ForecastService;
import ru.dmitryobukhoff.services.WeatherAPIService;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/find")
public class FindForecastServlet extends BaseServlet {
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
    private final ForecastService forecastService = new ForecastService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        webContext.setVariable("authenticated", authenticated);
        String city = request.getParameter("city");
        ForecastDto forecast = weatherAPIService.findForecastByLocation(city);
        if(forecast == null) throw new WeatherAPIException("No result of request");
        else webContext.setVariable("forecast", forecast);
        webContext.setVariable("added", forecastService.isUserHasLocation(sessionId, forecast.getCoordinates()));
        templateEngine.process("find", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(!authenticated) {
            throw new SessionNotExistExceptions("Session is not exist");
        }
        Double longitude = Double.parseDouble(request.getParameter("longitude"));
        Double latitude = Double.parseDouble(request.getParameter("latitude"));
        User user = forecastService.getUserBySession(sessionId);
        Location location = new Location(user, latitude, longitude);
        forecastService.addLocation(location);
        response.sendRedirect("/index");
    }
}
