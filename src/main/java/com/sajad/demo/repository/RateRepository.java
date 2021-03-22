package com.sajad.demo.repository;

import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.QRate;
import com.sajad.demo.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long>, QuerydslPredicateExecutor<Rate>,
        QuerydslBinderCustomizer<QRate> {

    Optional<Rate> findByUserIdAndProductId(long userId, long productId);

    @Override
    @SuppressWarnings("unchecked")
    default void customize(QuerydslBindings bindings, QRate rate) {
        bindings.bind(rate.status)
                .first((path, value) -> path.eq(CommentRateStatus.valueOf(value.name())));
    }
}
