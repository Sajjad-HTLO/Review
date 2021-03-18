package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.dto.CommentNewDto;
import com.sajad.demo.dto.CommentUpdateDto;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.service.comment.CommentService;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.sajad.demo.utility.Constants.UrlMappings.*;

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
    public ResponseEntity newComment(@Validated @RequestBody CommentNewDto commentNewDto) throws CommentNotAllowedException {
        Product product = productService.getById(commentNewDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        // Check the privacy of commenting
        // If the product is commentable to the previous buyers only, complain if the proncipal is
        // not previously bought this product
        if (!product.isCommentable() || (!product.isCommentableToPublic() && !commentNewDto.getIsBuyer()))
            throw new CommentNotAllowedException();

        Comment newComment = commentConverters.getByNewDto(commentNewDto);

        product.getComments().add(newComment);
        productService.persistUpdatedProduct(product);

        return ResponseEntity.noContent().build();
    }

    /**
     * Update (Approve or Reject) a comment. (By admin)
     *
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCommentStatus(@PathVariable long id,
                                              @Validated @RequestBody CommentUpdateDto updateDto) {
        Comment comment = commentService.getById(id).orElseThrow(ResourceNotFoundException::new);

        comment.setStatus(updateDto.getDecision());

        commentService.persistNewComment(comment);

        return ResponseEntity.noContent().build();
    }

}
