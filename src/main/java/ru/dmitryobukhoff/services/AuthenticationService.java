package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.exceptions.NotUniqueLoginException;
import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.repositories.SessionRepository;
import ru.dmitryobukhoff.repositories.UserRepository;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository = new UserRepository();
    private final SessionRepository sessionRepository = new SessionRepository();

    public User findUserByLogin(String login) throws NotUniqueLoginException {
        Optional<User> user = userRepository.findUserByLogin(login);
        if(user.isEmpty())
            throw new NotUniqueLoginException("Такой логин отсутствует");
        return user.get();
    }
    public boolean isPasswordCorrect(String password1, String password2){
        return password1.equals(password2);
    }

    public int addSession(Session session){
        return sessionRepository.createSessionWithId(session);
    }

    public boolean hasSession(User user){
        return (user.getSession() != null);
    }

    public void updateExpirationTime(Session session){
        session.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        sessionRepository.update(session);
    }

    public int createSessionForUser(User user){
        Session session = new Session(user, LocalDateTime.now().plusMinutes(5));
        int id = sessionRepository.createSessionWithId(session);
        user.setSession(session);
        return id;
    }

    public Optional<Cookie> findAuthenticateCookie(Cookie[] cookies){
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("session_id"))
                    return Optional.of(cookie);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    public boolean isSessionAlive(int sessionId){
        Session session = sessionRepository.read(sessionId);
        return session.getExpiresAt().isAfter(LocalDateTime.now());
    }

    public boolean isNeedToRemember(String remember){
        return !(remember == null);
    }
}
