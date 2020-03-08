package com.app.repo.impl;


import com.app.model.Customer;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

@Component
public class CustomerRepositoryImpl extends AbstractCrudRepository<Customer, Long> implements CustomerRepository {
    @Autowired
    public CustomerRepositoryImpl() {
        super();
    }


    @Override
    public Optional<Customer> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> findByLogin(String login) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<Customer> customer = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            customer = em
                    .createQuery("select c from Customer c where c.login = :login", Customer.class)
                    .setParameter("login", login)
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
        return customer;
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<Customer> customer = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            customer = em
                    .createQuery("select c from Customer c where c.email = :email", Customer.class)
                    .setParameter("email", email)
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
        return customer;
    }
}
