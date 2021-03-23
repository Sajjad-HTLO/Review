package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.converter.ProductConverters;
import com.sajad.demo.converter.RateConverters;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.dto.comment.CommentNewDto;
import com.sajad.demo.dto.product.ProductListDto;
import com.sajad.demo.dto.product.ProductUpdateDto;
import com.sajad.demo.dto.rate.RateNewDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.RateNotAllowedException;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.sajad.demo.helper.Constants.UrlMappings.PRODUCT_API;

@RestController
@RequestMapping(PRODUCT_API)
public class ProductController {

    private final ProductService productService;

    private final ProductConverters productConverters;

    private final CommentConverters commentConverters;

    private final RateConverters rateConverters;

    @Autowired
    public ProductController(ProductService productService, ProductConverters productConverters,
                             CommentConverters commentConverters, RateConverters rateConverters) {
        this.productService = productService;
        this.productConverters = productConverters;
        this.commentConverters = commentConverters;
        this.rateConverters = rateConverters;
    }

    @GetMapping
    public ResponseEntity listProducts(@QuerydslPredicate(root = Product.class) Predicate predicate,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        List<ProductListDto> listView = productService.listProducts(predicate, pageable)
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

    /**
     * The user issues a new (unverified) comment for a product.
     * (We may want to prevent repeated commenting as well)
     *
     * @return 204 status if successful.
     */
    @PostMapping("/{product-id}/comments")
    public ResponseEntity newProductComment(@PathVariable("product-id") long id,
                                            @Validated @RequestBody CommentNewDto newDto)
            throws CommentNotAllowedException {
        Comment newComment = commentConverters.getByNewDto(newDto);
        Product product = productService.getById(id).orElseThrow(ResourceNotFoundException::new);

        productService.newComment(product, newComment, newDto.getIsBuyer());

        return ResponseEntity.noContent().build();
    }

    /**
     * The user issues a new (unverified) comment for a product.
     * (We may want to prevent repeated commenting as well)
     *
     * @return 204 status if successful.
     */
    @PostMapping("/{product-id}/rates")
    public ResponseEntity newProductRate(@PathVariable("product-id") long id, @Validated @RequestBody RateNewDto newDto)
            throws RateNotAllowedException {
        Rate newRate = rateConverters.fromNewDto(newDto);
        Product product = productService.getById(id).orElseThrow(ResourceNotFoundException::new);

        productService.newRate(product, newRate, newDto.getIsBuyer());

        return ResponseEntity.noContent().build();
    }
}
