package com.jazva.challenge.repository;

import com.jazva.challenge.model.Location;
import com.jazva.challenge.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
public interface LocationRepository extends CrudRepository<Location, Integer> {
    Optional<Location> findByLocation(String location);
    @Modifying
    @Transactional
    List<Product> getById(int id);
}
