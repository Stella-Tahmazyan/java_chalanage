package com.jazva.challenge.service;

import com.jazva.challenge.model.Location;
import com.jazva.challenge.model.Product;
import com.jazva.challenge.model.ProductLocation;
import com.jazva.challenge.repository.LocationRepository;
import com.jazva.challenge.repository.ProductLocationRepository;
import com.jazva.challenge.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductLocationService {

    @Autowired
    private ProductLocationRepository productLocationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LocationRepository locationRepository;

    public List<ProductLocation> findAllByProductId(int id) {
        Product product = productRepository.getById(id);
        if (product != null) {
            return productLocationRepository.findAllByProductId(id);
        }
        log.error("Don't have product like that " + id);

        return null;
    }

    public ProductLocation findByLocationIdAndProductId(int locationId, int productId) {
        Location location = locationRepository.getById(locationId);
        Product product = productRepository.getById(productId);

        if (product != null && location != null) {
            return productLocationRepository.findByLocationIdAndProductId(location.getId(),
                    product.getId());
        }
        log.error("Don't have " + locationId + "product id and " +
                productId + " inventory.");
        return null;
    }

    public boolean save(ProductLocation productLocation) {
        Location location = locationRepository.getById(productLocation.getLocation().getId());
        Product product = productRepository.getById(productLocation.getProduct().getId());
        if (location != null && product != null) {
            productLocationRepository.save(productLocation);
            return true;
        }
        log.error("Cant save inventory with  " + productLocation.getLocation().getId() + "product id and " +
                productLocation.getProduct().getId() + " inventory.");
        return false;
    }

    public boolean updateSaved(int id) {
        Product product = productRepository.getById(id);
        if (product != null) {
            productLocationRepository.updateSaved(id);
            return true;
        }
        log.error("Don't have like that product id " + id);
        return false;
    }

    public int sumCount(int id) {
        Product product = productRepository.getById(id);
        if (product != null) {
            return productLocationRepository.sumCount(id);
        }
        log.error("Don't have like that product id " + id);
        return -1;
    }
}

