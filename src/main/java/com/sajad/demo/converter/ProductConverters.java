package com.sajad.demo.converter;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.CommentVoteStatus;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Vote;
import com.sajad.demo.dto.ProductListDto;
import com.sajad.demo.dto.ProductUpdateDto;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
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

            if (product.isCommentsVisibleToPublic())
                listDto.setCommentableToPublic(true);
        }

        /*
        Resolve the voting status
        If the product is votable but not to the public, then it's votable to the buyers only.
         */
        if (product.isVotable()) {
            listDto.setVotable(true);

            if (product.isVotesVisibleToPublic())
                listDto.setVotableToPublic(true);
        }

        // Set last 3 verified comments
        Set<Comment> verifiedComments = product.getComments().stream()
                .filter(comment -> comment.getStatus() == CommentVoteStatus.VERIFIED)
                .collect(Collectors.toSet());

        listDto.setLastComments(
                verifiedComments.stream()
                        .map(CommentConverters::fromComment)
                        .collect(Collectors.toSet()));

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
        return product.getVotes().stream()
                .filter(vote -> vote.getStatus() == CommentVoteStatus.VERIFIED)
                .map(Vote::getRate)
                .mapToInt(Integer::intValue)
                .summaryStatistics()
                .getAverage();
    }
}
