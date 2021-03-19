package com.sajad.demo.service.vote;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleVoteService implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public SimpleVoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public Page<Rate> listVotes(Predicate predicate, Pageable pageable) {
        return voteRepository.findAll(predicate, pageable);
    }

    @Override
    public Optional<Rate> getById(long id) {
        return voteRepository.findById(id);
    }

    /**
     * This is used to detect duplicate voting.
     */
    @Override
    public Optional<Rate> getByUserIdAndProductId(long userId, long productId) {
        return voteRepository.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public void persistNewVote(Rate newRate) {
        voteRepository.save(newRate);
    }
}
