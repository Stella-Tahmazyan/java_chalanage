package com.jazva.challenge.repository;

import com.jazva.challenge.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    Product getById(int id);
}
