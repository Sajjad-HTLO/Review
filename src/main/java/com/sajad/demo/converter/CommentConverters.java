package com.sajad.demo.converter;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.CommentDto;
import com.sajad.demo.dto.CommentNewDto;
import com.sajad.demo.exception.General4XXException;
import com.sajad.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class CommentConverters {

    private final UserService userService;

    @Autowired
    public CommentConverters(UserService userService) {
        this.userService = userService;
    }

    public Comment getByNewDto(CommentNewDto newDto) {
        User commentOwner = userService.getById(newDto.getUserId()).orElseThrow(General4XXException::new);

        Comment comment = new Comment();

        comment.setDate(Date.from(Instant.now()));
        comment.setContent(newDto.getContent());
        comment.setUser(commentOwner);

        return comment;
    }

    public static CommentDto fromComment(Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setContent(comment.getContent());

        return dto;
    }
}
