package com.srecommerce.order.services;

import com.srecommerce.order.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product getProductById(Long productId) {
        return restTemplate.getForObject("http://product.default.svc.cluster.local/products/" + productId, Product.class);
    }
}
