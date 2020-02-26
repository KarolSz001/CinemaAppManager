package com.app.repo.connection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private final EntityManagerFactory entityManagerFactory;
    private static DbConnection instance;

    private DbConnection(String persistenceUnit) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
    }



    public static DbConnection getInstance(String persistenceUnit){
        if(instance == null)
            instance = new DbConnection(persistenceUnit);
        return instance;
     }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}
