package com.sajad.demo.controller;

import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.CommentNewDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.General4XXException;
import com.sajad.demo.service.CommentService;
import com.sajad.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
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
     * The user issues a new (unverified) comment for a product.
     *
     * @return 204 status if successful.
     */
    @PostMapping
    public ResponseEntity newComment(@Validated @RequestBody CommentNewDto commentNewDto) throws CommentNotAllowedException {
        Product product = productService.getById(commentNewDto.getProductId()).orElseThrow(General4XXException::new);

        if (!product.isCommentable())
            throw new CommentNotAllowedException();

        // Else, persist the new comment
        Comment newComment = commentConverters.getByNewDto(commentNewDto);
        commentService.persistNewComment(newComment);

        return ResponseEntity.noContent().build();
    }

}
