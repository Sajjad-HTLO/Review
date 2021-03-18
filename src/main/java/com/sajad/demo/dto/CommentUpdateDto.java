package com.sajad.demo.dto;

import com.sajad.demo.domain.CommentVoteStatus;

import javax.validation.constraints.NotNull;

public class CommentUpdateDto {

    @NotNull
    private CommentVoteStatus decision;

    public CommentVoteStatus getDecision() {
        return decision;
    }

    public void setDecision(CommentVoteStatus decision) {
        this.decision = decision;
    }
}
