package com.sajad.demo.dto;

import com.sajad.demo.domain.CommentVoteStatus;

import javax.validation.constraints.NotNull;

public class VoteNewDto {

    /**
     * Since we don't have a security context to be populated upon the authentication, we have to
     * rely on the user identifier passed from the client
     */
    @NotNull
    private Long userId;

    @NotNull
    private Long productId;

    /**
     * Initial status of the vote.
     */
    private CommentVoteStatus status = CommentVoteStatus.PENDING;

    /**
     * The actual vote value ranging from 1 to 5.
     */
    private Integer rate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CommentVoteStatus getStatus() {
        return status;
    }

    public void setStatus(CommentVoteStatus status) {
        this.status = status;
    }
}
