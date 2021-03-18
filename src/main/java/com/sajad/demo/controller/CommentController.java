package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.CommentNewDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.General4XXException;
import com.sajad.demo.service.comment.CommentService;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sajad.demo.utility.Constants.COMMENTS_API;

@RestController
@RequestMapping(COMMENTS_API)
public class CommentController {

    private final CommentService commentService;

    private final ProductService productService;

    private final CommentConverters commentConverters;

    @Autowired
    public CommentController(CommentService commentService, ProductService productService,
                             CommentConverters commentConverters) {
        this.commentService = commentService;
        this.productService = productService;
        this.commentConverters = commentConverters;
    }


    /**
     * Endpoint to list comments and filter them on demand.
     *
     * @param predicate
     * @return
     */
    @GetMapping
    public ResponseEntity listComments(@QuerydslPredicate(root = Comment.class) Predicate predicate) {
        return ResponseEntity.ok(
                commentService.allComments(predicate, null)
                        .stream()
                        .map(CommentConverters::fromComment));
    }

    /**
     * The user issues a new (unverified) comment for a product.
     *
     * @return 204 status if successful.
     */
    @PostMapping
    public ResponseEntity newComment(@RequestBody CommentNewDto commentNewDto) throws CommentNotAllowedException {
        System.out.println();
        if (true)
            throw new CommentNotAllowedException();

        Product product = productService.getById(commentNewDto.getProductId()).orElseThrow(General4XXException::new);

        if (!product.isCommentable())
            throw new CommentNotAllowedException();

        // Check the privacy of commenting

        // We need to find out if the user is a buyer of this product somehow!


        // Else, persist the new comment
        Comment newComment = commentConverters.getByNewDto(commentNewDto);
        commentService.persistNewComment(newComment);

        return ResponseEntity.noContent().build();
    }

}
