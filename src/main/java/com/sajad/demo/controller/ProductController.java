package com.sajad.demo.controller;

import com.sajad.demo.converter.ProductConverters;
import com.sajad.demo.domain.Product;
import com.sajad.demo.service.ProductService;
import com.sajad.demo.dto.ProductUpdateDto;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    private final ProductConverters productConverters;

    @Autowired
    public ProductController(ProductService productService, ProductConverters productConverters) {
        this.productService = productService;
        this.productConverters = productConverters;
    }

    /**
     * Endpoint to update individual product's states, include its visibility,
     * being commentable (to public or just to verifier buyers), votable (to public or just to verified buyers)
     *
     * @param id        The product identifier which its state is going to be changed.
     * @param updateDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateProductState(@PathVariable Long id,
                                             @Validated @RequestBody ProductUpdateDto updateDto) {
        Product updated = productConverters.convertFromUpdateDto(updateDto, id);

        productService.persistUpdatedProduct(updated);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<String> products() {

        return null;
    }
}
