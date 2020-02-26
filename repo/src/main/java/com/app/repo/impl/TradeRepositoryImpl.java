package com.app.repo.impl;

import com.app.model.Trade;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

@Component
public class TradeRepositoryImpl extends AbstractCrudRepository<Trade, Long> implements TradeRepository {


    @Autowired

    public TradeRepositoryImpl() {
        super();
    }

    @Override
    public Optional<Trade> findByName(String name) {

        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Trade> tradeByName = Optional.empty();

        try {

            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            tradeByName = em
                    .createQuery("select t from Trade t where t.name = :name", Trade.class)
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

        return tradeByName;
    }

}
