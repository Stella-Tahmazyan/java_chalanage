package com.jazva.challenge.endpoint;

import com.jazva.challenge.model.Location;
import com.jazva.challenge.model.Product;
import com.jazva.challenge.model.ProductLocation;
import com.jazva.challenge.repository.LocationRepository;
import com.jazva.challenge.repository.ProductLocationRepository;
import com.jazva.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class ProductLocationEndpoint {

    @Autowired
    private ProductLocationRepository productLocationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("product/{id}")
    public ResponseEntity get(@PathVariable("id") int id) {
        List<ProductLocation> list = productLocationRepository.findAllByProductId(id);
        Map<String, Integer> map = new HashMap<>();
        for (ProductLocation i : list) map.put(i.getLocation().getLocation(), i.getCount());
        return ResponseEntity.ok().body(map);
    }

    @PutMapping("productLocation")
    public ResponseEntity update(@RequestBody ProductLocation productLocation) {
        Optional<ProductLocation> product = productLocationRepository.findByLocationIdAndProductId(productLocation.getLocation().getId(), productLocation.getProduct().getId());
        if (product.isPresent()){
            int count = product.get().getCount();
            int sum = count + productLocation.getCount();

            product.get().setCount(sum > 0 ? sum : 0);

            productLocationRepository.save(product.get());
            return ResponseEntity.ok().body(product);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("productLocation/{productId}/{locationId}")
    public ResponseEntity delete(@PathVariable("productId") int product_id,@PathVariable("locationId") int location_id) {
        Optional<ProductLocation> product = productLocationRepository.findByLocationIdAndProductId(location_id, product_id);
        if (product.isPresent()) {
            productLocationRepository.delete(product.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("productLocation")
    public ResponseEntity add(@RequestBody ProductLocation productLocation) {
        Optional<ProductLocation> product = productLocationRepository.findByLocationIdAndProductId(productLocation.getLocation().getId(), productLocation.getProduct().getId());

        if(!product.isPresent()) {
            Optional<Product> getProduct = productRepository.findById(productLocation.getProduct().getId());
            Optional<Location> getLocation = locationRepository.findById(productLocation.getLocation().getId());
            if (getLocation.isPresent() && getProduct.isPresent()) {
                productLocationRepository.save(productLocation);
                return ResponseEntity.status(201).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    @PatchMapping("productLocation/{id}")
    public ResponseEntity updateCount(@PathVariable("id") int id) {
        Optional<ProductLocation> productLocation = productLocationRepository.findByProductId(id);
        if (productLocation.isPresent()) {
            productLocationRepository.updateSaved(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("productLocation/{id}")
    public ResponseEntity getSum(@PathVariable("id") int id) {
        Optional<ProductLocation> productLocation = productLocationRepository.findByProductId(id);
        if (productLocation.isPresent()) {
            int sum = productLocationRepository.sumCount(id);
            return ResponseEntity.ok().body(sum);
        }
        return ResponseEntity.badRequest().build();
    }

}

