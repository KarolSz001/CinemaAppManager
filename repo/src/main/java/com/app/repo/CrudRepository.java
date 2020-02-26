package com.app.repo;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    Optional<T> addOrUpdate(T t);

    Optional<T> findOne(ID id);
    List<T> findAll();

    Optional<T> delete(ID id);
    boolean deleteAll();
}
