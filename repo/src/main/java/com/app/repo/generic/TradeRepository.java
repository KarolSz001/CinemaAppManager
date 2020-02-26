package com.app.repo.generic;



import com.app.model.Trade;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface TradeRepository extends CrudRepository<Trade, Long> {
    Optional<Trade> findByName(String surName);
}
