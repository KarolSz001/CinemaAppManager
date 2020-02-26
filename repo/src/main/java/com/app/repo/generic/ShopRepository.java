package com.app.repo.generic;


import com.app.model.Shop;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface ShopRepository extends CrudRepository<Shop, Long> {
    Optional<Shop> findByName(String name);
}
