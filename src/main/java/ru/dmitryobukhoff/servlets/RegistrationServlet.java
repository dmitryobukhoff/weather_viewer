package ru.dmitryobukhoff.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.services.RegistrationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private TemplateEngine templateEngine;
    private final RegistrationService registrationService = new RegistrationService();

    @Override
    public void init() throws ServletException {
        templateEngine = (TemplateEngine) getServletContext().getAttribute("templateEngine");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebContext webContext = new WebContext(request, response,getServletContext());
        templateEngine.process("registration", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        WebContext webContext = new WebContext(request, response, getServletContext());
        if(!registrationService.isLoginValid(login)){
            request.setAttribute("login_error", "Логин должен состоять из 8-20 символов латиницы или кириллицы.\nИ не иметь " +
                    "специальных символов: '.', ',', '!', '@', ';', '?', ' ', '#'");
            templateEngine.process("registration", webContext, response.getWriter());
            return;
        }
        Optional<User> optionalUser = registrationService.findUserByLogin(login);
        if(optionalUser.isPresent()){
            request.setAttribute("login_error", "Такой логин уже существует");
            templateEngine.process("registration", webContext, response.getWriter());
        }
        else if(!password.equals(confirmPassword)){
            request.setAttribute("login", login);
            request.setAttribute("password_error", "Пароли должны совпадать.");
            templateEngine.process("registration", webContext, response.getWriter());
        }else{
            User user = new User(login, password);
            registrationService.createUser(user);
            response.sendRedirect("/authenticate");
        }

    }
}