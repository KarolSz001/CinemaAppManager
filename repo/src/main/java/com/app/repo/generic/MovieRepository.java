package com.app.repo.generic;

import com.app.model.LoyaltyCard;
import com.app.model.Movie;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findByName(String name);
}
