package com.app.repo.impl;




import com.app.model.Country;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

@Component
public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Long> implements CountryRepository {

    @Autowired

    public CountryRepositoryImpl () {
        super();
    }

    @Override
    public Optional<Country> findByName(String name) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<Country> country = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            country = em
                    .createQuery("select c from Country c where c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getResultStream().findFirst();

            tx.commit();

        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return country;
    }
}
