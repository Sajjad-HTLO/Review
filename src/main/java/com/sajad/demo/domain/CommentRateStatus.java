package com.sajad.demo.domain;

/**
 * Possible values for new {@linkplain Comment} or {@linkplain Rate}
 */
public enum CommentRateStatus {

    /**
     * The given comment or rate is not verified yet.
     */
    PENDING,

    /**
     * The given comment or rate is verified.
     */
    VERIFIED,

    /**
     * The given comment or rate is rejected.
     */
    REJECTED
}
