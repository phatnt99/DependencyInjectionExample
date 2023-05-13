package com.phatng.di.repository;

import com.phatng.di.annotation.Component;
import com.phatng.di.annotation.Inject;
import com.phatng.di.dbengine.ConsoleDatabaseManager;
import com.phatng.di.entity.Product;

import java.util.List;

@Component
public class ProductRepository {

    @Inject
    protected ConsoleDatabaseManager consoleDatabaseManager;

    public List<Product> getAllProducts() {
        return consoleDatabaseManager.query(Product.class);
    }

}
