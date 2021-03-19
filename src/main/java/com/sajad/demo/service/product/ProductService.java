package com.sajad.demo.service.product;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getById(long id);

    void persistUpdatedProduct(Product updated);

    Page<Product> listProducts(Predicate predicate, Pageable pageable);
}
