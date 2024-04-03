package ru.dmitryobukhoff.servlets;

import org.thymeleaf.context.WebContext;
import ru.dmitryobukhoff.exceptions.InvalidLoginException;
import ru.dmitryobukhoff.exceptions.MismatchPasswordException;
import ru.dmitryobukhoff.exceptions.NonExistentLoginException;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.services.RegistrationService;
import ru.dmitryobukhoff.utils.BCryptUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(value = "/registration")
public class RegistrationServlet extends BaseServlet {
    private final RegistrationService registrationService = new RegistrationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(authenticated) response.sendRedirect("/index");
        else templateEngine.process("registration", webContext, response.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        if(!registrationService.isLoginValid(login)){
            throw new InvalidLoginException("Логин должен состоять из 4-20 символов латиницы или кириллицы.\nИ не иметь " +
                    "специальных символов: '.', ',', '!', '@', ';', '?', ' ', '#'");
        }
        Optional<User> optionalUser = registrationService.findUserByLogin(login);
        if(optionalUser.isPresent()){
            throw new NonExistentLoginException("This login already exist");
        }
        if(!password.equals(confirmPassword)){
            throw new MismatchPasswordException("Password mus be equals");
        }
        User user = new User(login, BCryptUtil.hash(password));
        registrationService.createUser(user);
        response.sendRedirect("/authenticate");
    }
}