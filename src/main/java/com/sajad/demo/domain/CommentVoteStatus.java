package com.sajad.demo.domain;

/**
 * Possible values for new {@linkplain Comment} or {@linkplain Vote}
 */
public enum CommentVoteStatus {

    /**
     * The given comment or vote is not verified yet.
     */
    PENDING,

    /**
     * The given comment or vote is verified.
     */
    VERIFIED,

    /**
     * The given comment or vote is rejected.
     */
    REJECTED
}
