package com.sajad.demo.controller;

import com.sajad.demo.converter.ProductConverters;
import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.ProductListDto;
import com.sajad.demo.dto.ProductUpdateDto;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sajad.demo.utility.Constants.PRODUCT_API;

@RestController
@RequestMapping(PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    private final ProductConverters productConverters;

    @Autowired
    public ProductController(ProductService productService, ProductConverters productConverters) {
        this.productService = productService;
        this.productConverters = productConverters;
    }

    @GetMapping
    public ResponseEntity listProducts() {
        List<ProductListDto> listView = productService.getProducts()
                .stream()
                .map(productConverters::convertFromProduct)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listView);
    }

    /**
     * Endpoint to update an individual product's states, include its visibility,
     * being commentable (to public or just to verifier buyers), votable (to public or just to verified buyers)
     *
     * @param id        The product identifier, which its state is going to be changed.
     * @param updateDto
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateProductState(@PathVariable Long id, @Validated @RequestBody ProductUpdateDto updateDto) {
        Product updated = productConverters.convertFromUpdateDto(updateDto, id);

        productService.persistUpdatedProduct(updated);

        return ResponseEntity.noContent().build();
    }
}
