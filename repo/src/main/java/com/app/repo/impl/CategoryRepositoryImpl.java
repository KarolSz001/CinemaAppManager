package com.app.repo.impl;

import com.app.model.Category;
import com.app.repo.generic.AbstractCrudRepository;
import com.app.repo.generic.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

@Component
public class CategoryRepositoryImpl extends AbstractCrudRepository<Category, Long> implements CategoryRepository {
@Autowired
    public CategoryRepositoryImpl() {
        super();
    }

    @Override
    public Optional<Category> findByName(String name) {
        EntityManager em = null;
        EntityTransaction tx = null;

        Optional<Category> category = Optional.empty();

        try {
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            category = em
                    .createQuery("select c from Category c where c.name = :name", Category.class)
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
        return category;
    }
}
