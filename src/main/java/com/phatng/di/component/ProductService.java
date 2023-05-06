package com.phatng.di.component;

import com.phatng.di.annotation.Component;
import com.phatng.di.annotation.Inject;
import com.phatng.di.entity.Product;
import com.phatng.di.repository.ProductRepository;

import java.util.List;

@Component
public class ProductService {

    @Inject
    protected ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    };
}
