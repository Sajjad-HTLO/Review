package com.sajad.demo.service.comment;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public SimpleCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * Find all comments based on the given {@code predicate} and {@code pageable}
     *
     * @return Paged response of {@linkplain Comment}s.
     */
    @Override
    public Page<Comment> listComments(Predicate predicate, Pageable pageable) {
        return commentRepository.findAll(predicate, pageable);
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
