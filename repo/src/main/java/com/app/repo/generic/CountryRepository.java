package com.app.repo.generic;




import com.app.model.Country;
import com.app.repo.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Long> {
 Optional<Country> findByName(String name);

}
