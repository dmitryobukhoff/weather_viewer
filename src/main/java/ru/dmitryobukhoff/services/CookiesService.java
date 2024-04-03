package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.repositories.SessionRepository;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.util.Optional;

public class CookiesService {

    private final SessionRepository sessionRepository = new SessionRepository();
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
    public Session getSessionById(int id){
        return sessionRepository.read(id);
    }

}
