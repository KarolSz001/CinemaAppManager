package com.app.repo.generic;


import com.app.model.CustomerOrder;
import com.app.model.Product;
import com.app.repo.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerOrderRepository extends CrudRepository<CustomerOrder, Long> {
    List<CustomerOrder> query6(LocalDate min, LocalDate max);
    List<Product> query7(String surname, String name, String countryName);
    List<CustomerOrder> query8a();
    }
