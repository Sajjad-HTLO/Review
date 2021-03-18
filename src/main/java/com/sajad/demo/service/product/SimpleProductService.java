package com.sajad.demo.service.product;

import com.sajad.demo.domain.Product;
import com.sajad.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SimpleProductService implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public SimpleProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<Product> getById(long id) {

        return productRepository.findById(id);
    }

    @Override
    public void persistUpdatedProduct(Product updated) {
        productRepository.save(updated);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}
