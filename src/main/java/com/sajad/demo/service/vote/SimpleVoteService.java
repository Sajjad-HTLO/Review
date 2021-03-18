package com.sajad.demo.service.vote;

import com.sajad.demo.domain.Vote;
import com.sajad.demo.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleVoteService implements VoteService {

    private final VoteRepository voteRepository;

    @Autowired
    public SimpleVoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @Override
    public void persistNewVote(Vote newVote) {
        voteRepository.save(newVote);
    }
}
