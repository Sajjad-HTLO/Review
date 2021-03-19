package com.sajad.demo.service.product;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Product;
import com.sajad.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleProductService implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public SimpleProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> listProducts(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable);
    }

    @Override
    public Optional<Product> getById(long id) {
        return productRepository.findById(id);
    }

    @Override
    public void persistUpdatedProduct(Product updated) {
        productRepository.save(updated);
    }
}
