package com.app.repo.generic;

import com.app.model.Product;
import com.app.model.Stock;
import com.app.repo.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface StockRepository extends CrudRepository<Stock, Long> {
    List<Object[]> query4();
    List<Object[]> query5(String name);
    Optional<Stock> findOneByProductName(String productName);
    List<Product> findAllProducts();
    Optional<Integer> getQuantityProductInStock(String nameProduct);

}
