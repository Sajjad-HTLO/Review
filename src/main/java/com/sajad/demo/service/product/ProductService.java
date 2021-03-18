package com.sajad.demo.service.product;

import com.sajad.demo.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<Product> getById(long id);

    void persistUpdatedProduct(Product updated);

    List<Product> getProducts();
}
