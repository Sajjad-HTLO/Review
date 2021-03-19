package com.sajad.demo.service.vote;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VoteService {

    Page<Rate> listVotes(Predicate predicate, Pageable pageable);

    Optional<Rate> getById(long id);

    Optional<Rate> getByUserIdAndProductId(long userId, long productId);

    void persistNewVote(Rate newRate);
}
