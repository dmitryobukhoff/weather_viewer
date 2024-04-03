package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.exceptions.NotUniqueLoginException;
import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.repositories.SessionRepository;
import ru.dmitryobukhoff.repositories.UserRepository;
import ru.dmitryobukhoff.utils.BCryptUtil;

import java.time.LocalDateTime;
import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository = new UserRepository();
    private final SessionRepository sessionRepository = new SessionRepository();

    public Optional<User> findUserByLogin(String login) throws NotUniqueLoginException {
         return userRepository.findUserByLogin(login);
    }
    public boolean isPasswordCorrect(String password1, String password2){
        return BCryptUtil.verified(password1, password2);
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
    public int createSessionForUser(User user) {
        Session session = new Session(user, LocalDateTime.now().plusMinutes(5));
        int id = sessionRepository.createSessionWithId(session);
        user.setSession(session);
        return id;
    }
    public boolean isNeedToRemember(String remember){
        return !(remember == null);
    }
}
