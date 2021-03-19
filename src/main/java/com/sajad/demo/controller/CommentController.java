package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.DecisionDto;
import com.sajad.demo.dto.comment.CommentNewDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.helper.Utility;
import com.sajad.demo.service.comment.CommentService;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sajad.demo.helper.Constants.UrlMappings.COMMENTS_API;

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
     * The admin can see non-verified comments too. (needs checking role of the principal)
     */
    @GetMapping
    public ResponseEntity listComments(@QuerydslPredicate(root = Comment.class) Predicate predicate,
                                       @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
                commentService.listComments(predicate, pageable)
                        .stream()
                        .map(CommentConverters::fromComment));
    }

    /**
     * The user issues a new (unverified) comment for a product.
     * (We may want to prevent repeated commenting as well)
     *
     * @return 204 status if successful.
     */
    @PostMapping
    public ResponseEntity newComment(@Validated @RequestBody CommentNewDto newDto) throws CommentNotAllowedException {
        Product product = productService.getById(newDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        /*
        Check the commenting rules
        If the product is commentable to the previous buyers only, complain if the principal is
        not previously bought this product
         */
        if (Utility.isCommentingRulesViolated(product, newDto.getIsBuyer()))
            throw new CommentNotAllowedException();

        Comment newComment = commentConverters.getByNewDto(newDto);

        product.getComments().add(newComment);
        productService.persistUpdatedProduct(product);

        return ResponseEntity.noContent().build();
    }

    /**
     * Update (Approve or Reject) a comment's state (By an admin of course)
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCommentStatus(@PathVariable long id, @Validated @RequestBody DecisionDto updateDto) {
        Comment comment = commentService.getById(id).orElseThrow(ResourceNotFoundException::new);
        comment.setStatus(updateDto.getDecision());

        commentService.persistNewComment(comment);

        return ResponseEntity.noContent().build();
    }
}
