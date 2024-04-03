package ru.dmitryobukhoff.services;

import ru.dmitryobukhoff.models.Location;
import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.models.weather.json.Coordinates;
import ru.dmitryobukhoff.repositories.LocationRepository;
import ru.dmitryobukhoff.repositories.SessionRepository;

import java.util.Optional;

public class ForecastService {
    private final SessionRepository sessionRepository = new SessionRepository();
    private final LocationRepository locationRepository = new LocationRepository();
    public boolean isUserHasLocation(int sessionId, Coordinates coordinates){
        Session session = sessionRepository.read(sessionId);
        User user = session.getUser();
        Optional<Location> location = locationRepository.findLocationByCoordinates(coordinates, user);
        return location.isPresent();
    }
    public User getUserBySession(int sessionId){
        Session session = sessionRepository.read(sessionId);
        return session.getUser();
    }

    public void addLocation(Location location){
        locationRepository.create(location);
    }
}
