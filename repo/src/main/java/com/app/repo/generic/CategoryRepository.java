package com.app.repo.generic;



import com.app.model.Category;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
