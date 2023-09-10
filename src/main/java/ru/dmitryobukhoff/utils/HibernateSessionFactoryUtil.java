package ru.dmitryobukhoff.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory instance;

    public static synchronized SessionFactory getInstance(){
        if(instance == null)
            instance = new Configuration().configure().buildSessionFactory();
        return instance;
    }
}
