package com.app.repo.impl;



import com.app.model.Producer;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

@Component
public class ProducerRepositoryImpl extends AbstractCrudRepository<Producer, Long> implements ProducerRepository {

    @Autowired

    public ProducerRepositoryImpl() {
        super();
    }

    @Override
    public Optional<Producer> findByName(String name) {

        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Producer> producerByName = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            producerByName = em
                    .createQuery("select c from Producer c where c.name = :name", Producer.class)
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

        return producerByName;
    }


}
