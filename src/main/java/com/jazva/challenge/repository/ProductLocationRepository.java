package com.jazva.challenge.repository;

import com.jazva.challenge.model.ProductLocation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductLocationRepository extends CrudRepository<ProductLocation, Integer>  {
    List<ProductLocation> findAllByProductId(int id);
    Optional<ProductLocation> findByProductId(int id);

    Optional<ProductLocation> findByLocationIdAndProductId(int location, int product);

    @Modifying
    @Transactional
    @Query(value = "update product_location set count =0 where product_id =:id", nativeQuery = true)
    void updateSaved(@Param("id") int id);

    @Query(value = "select SUM(pl.count) FROM product_location pl WHERE product_id =:id", nativeQuery = true)
    int sumCount(@Param("id") int id);
}
