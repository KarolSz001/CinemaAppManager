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
    public Optional<Customer> findByName(String surName) {

            EntityManager em = null;
            EntityTransaction tx = null;
            Optional<Customer> customerBySurName = Optional.empty();

            try {
                em = emf.createEntityManager();
                tx = em.getTransaction();
                tx.begin();

                customerBySurName = em
                        .createQuery("select c from Customer c where c.surname = :surname", Customer.class)
                        .setParameter("surname", surName)
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

        return customerBySurName;
        }

    }
