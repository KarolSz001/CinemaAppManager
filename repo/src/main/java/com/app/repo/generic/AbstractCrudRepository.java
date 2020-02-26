package com.app.repo.generic;


import com.app.repo.CrudRepository;
import com.app.repo.connection.DbConnection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    private final Class<T> entityType = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    private final Class<ID> idType = (Class<ID>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];

    protected final EntityManagerFactory emf;

    public AbstractCrudRepository() {
        emf = DbConnection.getInstance("HBN").getEntityManagerFactory();
    }


    @Override
    public Optional<T> addOrUpdate(T t) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<T> element = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            element = Optional.of(em.merge(t));
//            em.persist(t);
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
        return element;
    }

    @Override
    public Optional<T> findOne(ID id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<T> element = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            element = Optional.ofNullable(em.find(entityType, id));
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

        return element;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = null;
        EntityTransaction tx = null;

        List<T> elements = new ArrayList<>();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            elements = em
                    .createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
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

    @Override
    public Optional<T> delete(ID id) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<T> element = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            var elementToRemove = em.find(entityType, id);
            if (elementToRemove != null) {
                element = Optional.ofNullable(elementToRemove);
                em.remove(elementToRemove);
            }
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
        return element;
    }

    @Override
    public boolean deleteAll() {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            List<T> elements = em
                    .createQuery("select e from " + entityType.getSimpleName() + " e", entityType)
                    .getResultList();
            for (var e : elements) {
                em.remove(e);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return true;
    }


}
