package com.sajad.demo.converter;

import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.ProductUpdateDto;
import com.sajad.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverters {

    private final ProductService productService;

    @Autowired
    public ProductConverters(ProductService productService) {
        this.productService = productService;
    }

    public Product convertFromUpdateDto(ProductUpdateDto updateDto, long id) {
        Product product = productService.getById(id).orElseThrow(null);

        product.setVisible(updateDto.isVisible());


        return product;
    }
}
