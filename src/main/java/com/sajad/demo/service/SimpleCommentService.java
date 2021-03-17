package com.sajad.demo.service;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public SimpleCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Optional<Comment> getById(long id) {
        return commentRepository.findById(id);

    }

    @Override
    public void persistNewComment(Comment comment) {
        commentRepository.save(comment);
    }
}
