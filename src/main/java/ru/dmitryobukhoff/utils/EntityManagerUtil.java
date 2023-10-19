package ru.dmitryobukhoff.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;

public class EntityManagerUtil {
    private static EntityManagerFactory entityManagerFactory;

    public synchronized static EntityManagerFactory  getInstance(){
        if(entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("weather_db_h2");
        }
        return entityManagerFactory;
    }
}
