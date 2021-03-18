package com.sajad.demo.service.comment;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Comment;
import com.sajad.demo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleCommentService implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public SimpleCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    /**
     * (We may need pagination as well)
     *
     * @return
     */
    @Override
    public List<Comment> allComments(Predicate predicate, Pageable pageable) {
        List<Comment> result = new ArrayList<>();

        commentRepository.findAll(predicate, pageable)
                .forEach(result::add);

        return result;
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
