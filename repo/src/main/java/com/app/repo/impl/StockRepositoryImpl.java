package com.app.repo.impl;

import com.app.model.Product;
import com.app.model.Stock;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

@Component
public class StockRepositoryImpl extends AbstractCrudRepository<Stock, Long> implements StockRepository {

    @Autowired

    public StockRepositoryImpl() {
        super();
    }

    public Optional<Integer> getQuantityProductInStock(String nameProduct) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Integer> result = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            result = em
                    .createQuery("select s.quantity from Stock s join s.product p where p.name = :nameProduct", Integer.class)
                    .setParameter("nameProduct", nameProduct)
                    .getResultStream()
                    .findFirst();

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
        return result;
    }

    public List<Product> findAllProducts() {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<Product> result = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            result = em
                    .createQuery("select p from Stock s join s.product p",Product.class)
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
        return result;

    }

    public Optional<Stock> findOneByProductName(String productName) {
        EntityManager em = null;
        EntityTransaction tx = null;
        Optional<Stock> result = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            result = em
                    .createQuery("select s from Stock s join s.product p where p.name = : productName", Stock.class)
                    .setParameter("productName", productName)
                    .getResultStream()
                    .findFirst();

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
        return result;
    }

    public List<Object[]> query4() {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<Object[]> resultList = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            resultList = em
                    .createQuery("select p, ss from Stock s join s.product p join s.shop ss", Object[].class)
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
        return resultList;
    }

    public List<Object[]> query5(String tradeName) {
        EntityManager em = null;
        EntityTransaction tx = null;
        List<Object[]> resultList = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            resultList = em
                    .createQuery("select p, s.quantity from Stock s join s.product.producer p where p.trade.name =:tradeName", Object[].class)
                    .setParameter("tradeName", tradeName)
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
        return resultList;
    }


}
