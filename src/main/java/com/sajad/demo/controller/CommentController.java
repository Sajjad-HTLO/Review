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

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
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
     * Update (Approve or Reject) a comment's state (By an admin of course)
     */
    @PutMapping("/{id}")
    public ResponseEntity updateCommentStatus(@PathVariable long id, @Validated @RequestBody DecisionDto updateDto) {
        Comment comment = commentService.getById(id).orElseThrow(ResourceNotFoundException::new);

        commentService.updateCommentStatus(comment, updateDto.getDecision());

        return ResponseEntity.noContent().build();
    }
}
