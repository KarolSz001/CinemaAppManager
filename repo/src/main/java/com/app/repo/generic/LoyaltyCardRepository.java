package com.app.repo.generic;

import com.app.model.Customer;
import com.app.model.LoyaltyCard;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface LoyaltyCardRepository extends CrudRepository<LoyaltyCard, Long> {
    Optional<LoyaltyCard> findByName(String name);
}
