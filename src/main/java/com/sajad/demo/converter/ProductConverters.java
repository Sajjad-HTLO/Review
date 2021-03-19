package com.sajad.demo.converter;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.dto.ProductListDto;
import com.sajad.demo.dto.ProductUpdateDto;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public ProductListDto convertFromProduct(Product product) {
        ProductListDto listDto = new ProductListDto();

        listDto.setId(product.getId());
        listDto.setName(product.getName());
        listDto.setVisible(product.isVisible());

        /*
        Resolve the commenting status
        If the product is commentable but not to the public, then it's commentable to the buyers only.
         */
        if (product.isCommentable()) {
            listDto.setCommentable(true);

            if (product.isCommentableToPublic())
                listDto.setCommentableToPublic(true);
        }

        /*
        Resolve the voting status
        If the product is votable but not to the public, then it's votable to the buyers only.
         */
        if (product.isRatable()) {
            listDto.setVotable(true);

            if (product.isRatableToPublic())
                listDto.setVotableToPublic(true);
        }

        // Set last 3 verified comments (SORTED BY DATE)
        List<Comment> verifiedComments = product.getComments().stream()
                .filter(comment -> comment.getStatus() == CommentRateStatus.VERIFIED)
                .sorted(Comparator.comparing(Comment::getDate).reversed())
                .limit(3)
                .collect(Collectors.toList());

        listDto.setComments(
                verifiedComments.stream()
                        .map(CommentConverters::fromComment)
                        .collect(Collectors.toList()));

        // Set verified comments count
        listDto.setCommentsCount(verifiedComments.size());

        // Set avg rate
        listDto.setRatesAverage(getAverageVerifiedRate(product));

        return listDto;
    }

    /**
     * Finds given {@code product}'s verified rates' average value.
     *
     * @param product
     * @return
     */
    private double getAverageVerifiedRate(Product product) {
        return product.getRates().stream()
                .filter(rate -> rate.getStatus() == CommentRateStatus.VERIFIED)
                .map(Rate::getValue)
                .mapToInt(Integer::intValue)
                .summaryStatistics()
                .getAverage();
    }
}
