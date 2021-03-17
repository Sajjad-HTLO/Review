package com.sajad.demo.converter;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.CommentNewDto;
import com.sajad.demo.exception.General4XXException;
import com.sajad.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        comment.setContent(newDto.getContent());
        comment.setUser(commentOwner);

        return comment;
    }
}
