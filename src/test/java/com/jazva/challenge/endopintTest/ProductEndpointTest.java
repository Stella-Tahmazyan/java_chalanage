package com.jazva.challenge.endopintTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jazva.challenge.model.Location;
import com.jazva.challenge.model.Product;
import com.jazva.challenge.model.ProductLocation;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductEndpointTest {

    @Test
    public void getSum() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/productSum/12";
        ResponseEntity<Integer> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Integer>() {
                });
        Integer expected = 110;
        Assert.assertEquals(expected, response.getBody());
    }

    @Test
    public void getMap() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/product/12";
        ResponseEntity<Map<String, Integer>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Integer>>() {
                });

        String expected = "{\"gyumri\": 50,\"Yerevan\": 60}";
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(response.getBody());
        JSONAssert.assertEquals(expected, json, JSONCompareMode.LENIENT);
    }

    @Test
    public void get() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/product";
        Product product = Product.builder().id(12).name("Pankaj").price(25).build();
        Location location = Location.builder().id(1).location("gyumri").build();

        ProductLocation expected = ProductLocation.builder()
                .id(1).location(location).product(product).count(75).build();

        ProductLocation request = ProductLocation.builder().
                location(location).product(product).count(25).build();

        HttpEntity<ProductLocation> reqt = new HttpEntity<>(request);
        ResponseEntity<ProductLocation> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                reqt,
                new ParameterizedTypeReference<ProductLocation>() {
                });
        Assert.assertEquals(response.getBody(), expected);
    }

    @Test
    public void deleteCount() throws IOException, JSONException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/product/12";
        ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                null,
                Boolean.class);
        Assert.assertEquals(response.getBody(), true);
    }
}