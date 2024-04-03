package ru.dmitryobukhoff.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/logout")
public class LogoutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Cookie> cookieOptional = cookiesService.findAuthenticateCookie(request.getCookies());
        cookieOptional.ifPresent(cookie -> {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        });
        response.sendRedirect("/index");
    }
}
