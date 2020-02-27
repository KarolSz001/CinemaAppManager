package com.app.repo.generic;

import com.app.model.Customer;
import com.app.repo.CrudRepository;
import jdk.jfr.Category;

import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Optional<Customer> findByName(String name);
}
