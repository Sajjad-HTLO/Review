package com.sajad.demo.service.comment;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {

    Page<Comment> listComments(Predicate predicate, Pageable pageable);

    Optional<Comment> getById(long id);

    void persistNewComment(Comment comment);
}
