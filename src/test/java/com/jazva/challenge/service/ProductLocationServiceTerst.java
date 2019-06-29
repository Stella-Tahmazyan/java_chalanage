package com.jazva.challenge.service;

import com.google.common.base.Optional;
import com.jazva.challenge.model.Location;
import com.jazva.challenge.model.Product;
import com.jazva.challenge.model.ProductLocation;
import com.jazva.challenge.repository.LocationRepository;
import com.jazva.challenge.repository.ProductLocationRepository;
import com.jazva.challenge.repository.ProductRepository;
import javafx.beans.binding.When;
import jdk.nashorn.internal.runtime.options.Option;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static junit.framework.TestCase.assertFalse;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ProductLocationServiceTerst {

    @InjectMocks
    ProductLocationService productLocationService;

    @Mock
    ProductRepository productRepository;

    @Mock
    ProductLocationRepository productLocationRepository;
    @Mock
    LocationRepository locationRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAllByProductId() {
        List<ProductLocation> list = new ArrayList<>();
        Product product = Product.builder().id(12).name("Pankaj").price(25).build();
        Location location = Location.builder().id(1).location("gyumri").build();
        ProductLocation expected = ProductLocation.builder()
                .id(1).location(location).product(product).count(0).build();
        list.add(expected);

        product = Product.builder().id(12).name("Pankaj").price(25).build();
        location = Location.builder().id(2).location("gyumri").build();
        expected = ProductLocation.builder()
                .id(1).location(location).product(product).count(0).build();
        list.add(expected);

        when(productRepository.getById(12)).thenReturn(product);

        when(productLocationRepository.findAllByProductId(12)).thenReturn(list);

        List<ProductLocation> productLocationList = productLocationService.findAllByProductId(12);

        assertEquals(list.size(), productLocationList.size());
        assertEquals(list.get(0).getId(), productLocationList.get(0).getId());
        assertEquals(list.get(0).getCount(), productLocationList.get(0).getCount());
    }

    @Test
    public void findByLocationIdAndProductId() {
        Product product = Product.builder().id(12).name("Pankaj").price(25).build();
        Location location = Location.builder().id(1).location("gyumri").build();
        ProductLocation expected = ProductLocation.builder()
                .id(1).location(location).product(product).count(0).build();


        when(productRepository.getById(12)).thenReturn(product);
        when(locationRepository.getById(1)).thenReturn(location);
        when(productLocationRepository.findByLocationIdAndProductId(1, 12)).thenReturn(expected);

        ProductLocation productLocation = productLocationService.findByLocationIdAndProductId(1, 12);
        assertEquals(productLocation.getId(), expected.getId());
        assertEquals(productLocation.getCount(), expected.getCount());
    }
}
