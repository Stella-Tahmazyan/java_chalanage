package com.jazva.challenge;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Loads stored objects from the file system and builds up
 * the appropriate objects to add to the data source.
 *
 */
@Component
public class ProductLoader implements InitializingBean {
    @Value("classpath:data/products.txt")
    private Resource products;

    @Autowired
    DataSource dataSource;

    /**
     * Load the products into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(products.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                /* TODO: Create appropriate objects and save them to
                 *       the datasource.
                 */
            }
        }
    }

}
