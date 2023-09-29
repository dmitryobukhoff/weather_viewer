package ru.dmitryobukhoff.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import ru.dmitryobukhoff.models.User;
import ru.dmitryobukhoff.utils.EntityManagerUtil;

import java.util.List;
import java.util.Optional;

public class UserRepository implements CrudRepository<User> {
    private final EntityManagerFactory entityManagerFactory = EntityManagerUtil.getInstance();

    @Override
    public void create(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public User read(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public void update(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        User user1 = entityManager.find(User.class, user.getId());
        user1.setLogin(user.getLogin());
        user1.setPassword(user.getPassword());
        entityManager.getTransaction().commit();
    }

    @Override
    public void delete(User user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    public Optional<User> findUserByLogin(String login) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        List<User> user = query.getResultList();
        if(user.isEmpty())
            return Optional.empty();
        return Optional.ofNullable(user.get(0));
    }
}
