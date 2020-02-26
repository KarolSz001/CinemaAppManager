package com.app.repo.generic;


import com.app.model.Producer;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface ProducerRepository extends CrudRepository<Producer, Long> {
    Optional<Producer> findByName(String name);
}
