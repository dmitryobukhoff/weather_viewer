package ru.dmitryobukhoff.servlets;

import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.dmitryobukhoff.exceptions.*;
import ru.dmitryobukhoff.services.CookiesService;;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
public class BaseServlet extends HttpServlet {
    protected static TemplateEngine templateEngine;
    protected WebContext webContext;
    protected static CookiesService cookiesService = new CookiesService();
    protected Integer sessionId;
    protected boolean authenticated = false;

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
        super.init();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        webContext = new WebContext(req, resp, getServletContext());
        try {
            Optional<Cookie> cookie = cookiesService.findAuthenticateCookie(req.getCookies());
            if(cookie.isPresent()) {
                sessionId = Integer.parseInt(cookie.get().getValue());
                if(cookie.get().getMaxAge() == -1) authenticated = true;
                else authenticated = cookiesService.isSessionAlive(sessionId);
            }else authenticated = false;
            super.service(req, resp);
        }
        catch (NotUniqueLoginException | MismatchPasswordException | InvalidLoginException exception){
            webContext.setVariable("error", exception.getMessage());
            templateEngine.process("registration", webContext, resp.getWriter());
            log.warn(LocalDateTime.now() + exception.getMessage());
        }catch (WeatherAPIException exception){
            webContext.setVariable("error", exception.getMessage());
            if(req.getServletPath().startsWith("/find")){
                templateEngine.process("find", webContext, resp.getWriter());
            }else templateEngine.process("index", webContext, resp.getWriter());
            log.warn(LocalDateTime.now() + exception.getMessage());
        }catch (SessionNotExistExceptions exception){
            resp.sendRedirect("/index");
            log.warn(LocalDateTime.now().toString() +  exception.getMessage());
        }
    }




}
