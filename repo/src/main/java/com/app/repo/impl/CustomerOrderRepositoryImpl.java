package com.app.repo.impl;

import com.app.model.CustomerOrder;
import com.app.model.Product;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CustomerOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerOrderRepositoryImpl extends AbstractCrudRepository<CustomerOrder, Long> implements CustomerOrderRepository {

    @Autowired

    public CustomerOrderRepositoryImpl() {
        super();
    }


    public List<CustomerOrder> query6(LocalDate minDate, LocalDate maxDate) {
        EntityManager em = null;
        EntityTransaction tx = null;

        List<CustomerOrder> elements = new ArrayList<>();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            elements = em
                    .createQuery("select c from CustomerOrder c  where c.date < :maxDate and c.date > :minDate", CustomerOrder.class)
                    .setParameter("minDate",minDate)
                    .setParameter("maxDate",maxDate)
                    .getResultList();
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
        return elements;
    }

    public List<Product> query7(String surname, String name, String countryName) {
        EntityManager em = null;
        EntityTransaction tx = null;

        List<Product> elements = new ArrayList<>();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            elements = em
                    .createQuery("select p from CustomerOrder c join c.customer cc join c.product p where cc.surname =:surname and cc.name =: name and cc.country.name =:countryName", Product.class)
                    .setParameter("surname",surname)
                    .setParameter("name", name)
                    .setParameter("countryName", countryName)
                    .getResultList();

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
        return elements;
    }

    public List<CustomerOrder> query8a() {
        EntityManager em = null;
        EntityTransaction tx = null;

        List<CustomerOrder> elements = new ArrayList<>();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            elements = em
                    .createQuery("select c from CustomerOrder c join c.customer cc join c.product p where cc.country.name = p.producer.country.name",CustomerOrder.class)
                    .getResultList();

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
        return elements;
    }
}

