package com.phatng.di;

import com.phatng.di.component.ProductService;
import com.phatng.di.container.DIContainer;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        DIContainer diContainer = new DIContainer();

        ProductService productService = diContainer.getInstance(ProductService.class);
        System.out.println(productService.getAllProducts());
    }
}