package com.jazva.challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazva.challenge.model.Product;
import com.jazva.challenge.repository.ProductRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;

import java.util.ArrayList;

/**
 * Loads stored objects from the file system and builds up
 * the appropriate objects to add to the data source.
 */
@Component
public class ProductLoader implements InitializingBean {
    @Value("classpath:data/products.txt")
    private Resource products;

    @Autowired
    DataSource dataSource;

    @Autowired
    private ProductRepository productRepository;

    /**
     * Load the products into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load
     */
    @Override
    @Bean
    public void afterPropertiesSet() throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(products.getInputStream()))) {
            String line;
            Product product;
            ArrayList<Product> arrayList = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            while ((line = br.readLine()) != null) {
                product = objectMapper.readValue(line, Product.class);
                arrayList.add(product);
            }
            productRepository.saveAll(arrayList);
        }
    }
}