package com.jazva.challenge.endpoint;

import com.jazva.challenge.model.ProductLocation;
import com.jazva.challenge.service.ProductLocationService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProductEndpoint {

    @Autowired
    private ProductLocationService productLocationService;

    @ApiOperation(value = "Get all inventory for product", response = ProductLocation.class)
    @GetMapping("product/{id}")
    public ResponseEntity<Map<String, Integer>> get(@PathVariable("id") int id) {
        List<ProductLocation> list = productLocationService.findAllByProductId(id);
        if (list != null) {
            Map<String, Integer> map = new HashMap<>();
            for (ProductLocation i : list) map.put(i.getLocation().getLocation(), i.getCount());
            return ResponseEntity.ok().body(map);
        }
        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Add or reduce product count.", response = ProductLocation.class)
    @PutMapping("product")
    public ResponseEntity<ProductLocation> update(@RequestBody ProductLocation productLocation) {
        ProductLocation product = productLocationService.findByLocationIdAndProductId(
                productLocation.getLocation().getId(), productLocation.getProduct().getId());
        if (product != null) {
            int count = product.getCount();
            int sum = count + productLocation.getCount();

            product.setCount(sum > 0 ? sum : 0);

            if (productLocationService.save(product)) {
                return ResponseEntity.ok().body(product);
            }
            return ResponseEntity.badRequest().body(product);
        }
        return ResponseEntity.badRequest().build();
    }

    @ApiOperation(value = "Update product count.", response = ProductLocation.class)
    @PutMapping("product/{id}")
    public ResponseEntity<Boolean> updateCount(@PathVariable("id") int id) {
        if (productLocationService.updateSaved(id)) {
            return ResponseEntity.ok().body(productLocationService.updateSaved(id));
        }
        return ResponseEntity.badRequest().body(productLocationService.updateSaved(id));
    }

    @ApiOperation(value = "Get product count sum.", response = ProductLocation.class)
    @GetMapping("productSum/{id}")
    public ResponseEntity<Integer> getSum(@PathVariable("id") int id) {
        int sum = productLocationService.sumCount(id);
        if (sum > -1) {
            return ResponseEntity.ok().body(sum);
        }
        return ResponseEntity.badRequest().build();
    }
}
