package com.jazva.challenge.repository;

import com.jazva.challenge.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {

    Location getById(int id);

}
