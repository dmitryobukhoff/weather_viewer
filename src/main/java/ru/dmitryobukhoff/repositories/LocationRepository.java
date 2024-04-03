package ru.dmitryobukhoff.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import ru.dmitryobukhoff.models.Location;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.models.weather.json.Coordinates;
import ru.dmitryobukhoff.utils.EntityManagerUtil;

import java.util.List;
import java.util.Optional;

public class LocationRepository implements CrudRepository<Location>{

    private final EntityManagerFactory entityManagerFactory = EntityManagerUtil.getInstance();

    @Override
    public void create(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(location);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Location read(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Location location = entityManager.find(Location.class, id);
        entityManager.getTransaction().commit();
        return location;
    }

    @Override
    public void update(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Location locationForUpdate = read(location.getId());
        locationForUpdate.setLatitude(location.getLatitude());
        locationForUpdate.setLongitude(location.getLongitude());
        locationForUpdate.setName(location.getName());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Location location) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(location);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Optional<Location> findLocationByCoordinates(Coordinates coordinates, User user){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Location> query = entityManager.createQuery(
                "SELECT loc FROM Location loc WHERE loc.latitude = :latitude AND loc.longitude = :longitude AND loc.user = :user", Location.class
        );
        query.setParameter("longitude", coordinates.getLongitude());
        query.setParameter("latitude", coordinates.getLatitude());
        query.setParameter("user", user);
        List<Location> locations = query.getResultList();
        if(locations.isEmpty()) return Optional.empty();
        else return Optional.ofNullable(locations.get(0));
    }
}
