package ru.dmitryobukhoff.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    public synchronized static EntityManagerFactory  getInstance(){
        if(entityManagerFactory == null)
            entityManagerFactory = Persistence.createEntityManagerFactory("weather_db");
        return entityManagerFactory;
    }
}
