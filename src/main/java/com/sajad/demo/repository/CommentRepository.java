package com.sajad.demo.repository;

import com.sajad.demo.domain.Comment;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.QComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment>,
        QuerydslBinderCustomizer<QComment> {

    @Override
    @SuppressWarnings("unchecked")
    default void customize(QuerydslBindings bindings, QComment comment) {
        bindings.bind(comment.status)
                .first((path, value) -> path.eq(CommentRateStatus.valueOf(value.name())));
    }
}
