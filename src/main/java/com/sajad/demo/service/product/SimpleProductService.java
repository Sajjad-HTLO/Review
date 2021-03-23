package com.sajad.demo.service.product;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.RateNotAllowedException;
import com.sajad.demo.helper.Utility;
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
    public void newComment(Product product, Comment newComment, boolean isBuyer) throws CommentNotAllowedException {
        /*
        Check the commenting rules
        If the product is commentable to the previous buyers only, complain if the principal is
        not previously bought this product
         */
        if (Utility.isCommentingRulesViolated(product, isBuyer))
            throw new CommentNotAllowedException();

        product.getComments().add(newComment);

        productRepository.save(product);
    }

    @Override
    public void newRate(Product product, Rate newRate, Boolean isBuyer) throws RateNotAllowedException {
        /*
        Check the rating rules
        I didn't move this checking to voting service layer, because I don't like passing the dto instance to
        the service layer.
        As a predefined rule for repeated rating, we re-write the rate
         */
        if (Utility.isRatingRulesViolated(product, isBuyer))
            throw new RateNotAllowedException();

        // Check for duplicate rate and update the rate value
        Optional<Rate> duplicate= product.getRates().stream()
                .filter(rate -> rate.getUser().getId().equals(newRate.getUser().getId()))
                .findFirst();

        if (duplicate.isPresent()) {
            duplicate.ifPresent(updatedRate ->
                    product.getRates().stream()
                            // We're sure there duplicate rate exist
                            .filter(rate -> rate.getId().equals(updatedRate.getId()))
                            .findFirst().get()
                            .setValue(newRate.getValue()));
        } else {
            product.getRates().add(newRate);
        }

        productRepository.save(product);
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
