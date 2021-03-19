package com.sajad.demo.dto;

import com.sajad.demo.domain.CommentRateStatus;

import javax.validation.constraints.NotNull;

/**
 * Dto class to approve or reject a comment or vote.
 */
public class DecisionDto {

    @NotNull
    private CommentRateStatus decision;

    public CommentRateStatus getDecision() {
        return decision;
    }

    public void setDecision(CommentRateStatus decision) {
        this.decision = decision;
    }
}
