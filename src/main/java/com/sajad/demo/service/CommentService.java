package com.sajad.demo.service;

import com.sajad.demo.domain.Comment;

import java.util.Optional;

public interface CommentService {

    Optional<Comment> getById(long id);

    void persistNewComment(Comment comment);
}
