package com.jazva.challenge.endpoint;

import com.jazva.challenge.model.Location;
import com.jazva.challenge.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class LocationEndpoint {

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("location")
    public ResponseEntity add(@RequestBody Location location) {
        if (location != null || !StringUtils.isEmpty(location.getLocation())) {
            locationRepository.save(location);
            return ResponseEntity.status(201).body(location);
        }
        if (locationRepository.findByLocation(location.getLocation()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("location")
    public ResponseEntity delete(@PathVariable("id") int id) {
        if (locationRepository.findById(id).isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("location")
    public ResponseEntity update(@RequestBody Location location) {
        if (locationRepository.findById(location.getId()).isPresent()) {
            locationRepository.save(location);
            return ResponseEntity
                    .ok(location);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
