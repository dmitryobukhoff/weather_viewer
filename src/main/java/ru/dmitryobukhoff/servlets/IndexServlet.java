package ru.dmitryobukhoff.servlets;

import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.services.WeatherAPIService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/index")
public class IndexServlet extends BaseServlet {
    private final WeatherAPIService weatherAPIService = new WeatherAPIService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(authenticated){
            Session session = cookiesService.getSessionById(sessionId);
            webContext.setVariable("user", session.getUser());
        }
        System.out.println("Index authenticated: " + authenticated);
        webContext.setVariable("authenticated", authenticated);
        templateEngine.process("index", webContext, response.getWriter());
    }
}
