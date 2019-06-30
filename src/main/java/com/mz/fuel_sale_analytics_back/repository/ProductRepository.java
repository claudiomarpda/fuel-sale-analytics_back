package com.mz.fuel_sale_analytics_back.repository;

import com.mz.fuel_sale_analytics_back.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByName(String name);

}
