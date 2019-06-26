package com.jazva.challenge.repository;

import com.jazva.challenge.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAllById(Integer id);

}
