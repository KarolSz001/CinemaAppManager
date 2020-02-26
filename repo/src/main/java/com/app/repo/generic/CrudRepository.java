package com.app.repo.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    void add(T item);
    void update(ID id, T item);
    void delete(ID id);
    Optional<T> findOne(ID id);
    List<T> findAll();
}
