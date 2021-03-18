package com.sajad.demo.service.vote;

import com.sajad.demo.domain.Vote;

public interface VoteService {
    void persistNewVote(Vote newVote);
}
