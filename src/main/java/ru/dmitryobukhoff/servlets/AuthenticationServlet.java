package ru.dmitryobukhoff.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.dmitryobukhoff.exceptions.NotUniqueLoginException;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.services.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/authenticate")
public class AuthenticationServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private final AuthenticationService authenticationService = new AuthenticationService();

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> cookie = authenticationService.findAuthenticateCookie(cookies);
        if(cookie.isPresent()){
            int sessionId = Integer.parseInt(cookie.get().getValue());
            if(authenticationService.isSessionAlive(sessionId)) {
                response.sendRedirect("/index");
            }
            else{
                WebContext webContext = new WebContext(request,response, getServletContext());
                templateEngine.process("authenticate", webContext, response.getWriter());
            }
        }else{
            WebContext webContext = new WebContext(request,response, getServletContext());
            templateEngine.process("authenticate", webContext, response.getWriter());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        WebContext webContext = new WebContext(request, response, getServletContext());
        try{
            User user = authenticationService.findUserByLogin(login);
            boolean isPasswordCorrect = authenticationService.isPasswordCorrect(user.getPassword(), password);
            if(!isPasswordCorrect){
                request.setAttribute("wrong_password", "Пароль неверен.");
                templateEngine.process("authenticate", webContext, response.getWriter());
            }else{
                Cookie cookie;
                if(authenticationService.hasSession(user)){
                    authenticationService.updateExpirationTime(user.getSession());
                    cookie = new Cookie("session_id", Integer.toString(user.getSession().getId()));
                }else{
                    int id = authenticationService.createSessionForUser(user);
                    cookie = new Cookie("session_id", Integer.toString(id));
                }
                if(!authenticationService.isNeedToRemember(remember))
                    cookie.setMaxAge(-1);
                else
                    cookie.setMaxAge(300);
                cookie.setPath("/");
                response.addCookie(cookie);
                response.sendRedirect("/index");
            }
        }catch (NotUniqueLoginException e){
            request.setAttribute("wrong_login", e.getMessage());
            templateEngine.process("authenticate", webContext, response.getWriter());
        }
    }
}
