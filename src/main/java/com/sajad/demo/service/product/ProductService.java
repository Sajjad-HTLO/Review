package com.sajad.demo.service.product;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.RateNotAllowedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {

    Optional<Product> getById(long id);

    void persistUpdatedProduct(Product updated);

    Page<Product> listProducts(Predicate predicate, Pageable pageable);

    void newComment(Product product, Comment newComment,boolean isBuyer) throws CommentNotAllowedException;

    void newRate(Product product, Rate newRate, Boolean isBuyer) throws RateNotAllowedException;
}
