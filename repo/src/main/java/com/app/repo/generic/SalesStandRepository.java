package com.app.repo.generic;

import com.app.model.Movie;
import com.app.model.SalesStand;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface SalesStandRepository extends CrudRepository<SalesStand, Long> {
    Optional<SalesStand> findByName(String name);

}
