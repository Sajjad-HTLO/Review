package com.sajad.demo.converter;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.comment.CommentDto;
import com.sajad.demo.dto.comment.CommentNewDto;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentConverters {

    private final UserService userService;

    private final ProductService productService;

    @Autowired
    public CommentConverters(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    public Comment getByNewDto(CommentNewDto newDto) {
        User commentOwner = userService.getById(newDto.getUserId()).orElseThrow(ResourceNotFoundException::new);
        Product product = productService.getById(newDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        Comment comment = new Comment();

        comment.setContent(newDto.getContent());
        comment.setUser(commentOwner);
        comment.setProduct(product);

        return comment;
    }

    public static CommentDto fromComment(Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setStatus(comment.getStatus());
        dto.setDate(comment.getDate());

        return dto;
    }
}
