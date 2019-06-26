package com.jazva.challenge.endpoint;

import com.jazva.challenge.model.Product;
import com.jazva.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductEndpoint {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("product")
    public ResponseEntity add(@RequestBody Product product) {
        if (product != null && !StringUtils.isEmpty(product.getName()) && !StringUtils.isEmpty(product.getPrice())) {
            productRepository.save(product);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("product")
    public ResponseEntity delete(@PathVariable("id") int id) {
        if (productRepository.findById(id).isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("product")
    public ResponseEntity update(@RequestBody Product product) {
        if (productRepository.findById(product.getId()).isPresent()) {
            productRepository.save(product);
            return ResponseEntity
                    .ok(product);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}