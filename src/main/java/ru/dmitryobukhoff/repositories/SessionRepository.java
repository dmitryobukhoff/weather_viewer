package ru.dmitryobukhoff.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import ru.dmitryobukhoff.models.Session;
import ru.dmitryobukhoff.utils.EntityManagerUtil;

import java.util.Optional;

public class SessionRepository implements CrudRepository<Session> {

    private final EntityManagerFactory entityManagerFactory = EntityManagerUtil.getInstance();

    @Override
    public void create(Session session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(session);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Session read(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Session session = entityManager.find(Session.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return session;
    }

    @Override
    public void update(Session session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Session sessionToBeUpdated = entityManager.find(Session.class, session.getId());
        sessionToBeUpdated.setUser(session.getUser());
        sessionToBeUpdated.setExpiresAt(session.getExpiresAt());
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void delete(Session session) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(session);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public int createSessionWithId(Session session){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(session);
        entityManager.flush();
        int id = session.getId();
        entityManager.getTransaction().commit();
        return id;
    }
}
