package ru.dmitryobukhoff.servlets;

import org.thymeleaf.context.WebContext;
import ru.dmitryobukhoff.exceptions.NonExistentLoginException;
import ru.dmitryobukhoff.exceptions.WrongPasswordException;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.services.AuthenticationService;
import ru.dmitryobukhoff.services.CookiesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/authenticate")
public class AuthenticationServlet extends BaseServlet {
    private final AuthenticationService authenticationService = new AuthenticationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(authenticated) response.sendRedirect("/index");
        else templateEngine.process("authenticate", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        User user = authenticationService.findUserByLogin(login).orElseThrow(() -> (new NonExistentLoginException("Login not exist")));
        if(!authenticationService.isPasswordCorrect(password, user.getPassword()))
            throw new WrongPasswordException("Wrong password");

        int id;
        if(authenticationService.hasSession(user)) {
            authenticationService.updateExpirationTime(user.getSession());
            id = user.getSession().getId();
        }
        else id = authenticationService.createSessionForUser(user);

        Cookie cookie = new Cookie("session_id", Integer.toString(id));

        if(authenticationService.isNeedToRemember(remember)) cookie.setMaxAge(300);
        else cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.sendRedirect("/index");
    }
}
