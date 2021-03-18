package com.sajad.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Encapsulates a vote, ranging from 1 to 5.
 */
@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * We should handle NPE if this vote's owner has been deleted in the future anyway :)
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Integer rate;

    /**
     * Initial status of the vote.
     */
    private CommentVoteStatus status = CommentVoteStatus.PENDING;

    public Long getId() {
        return id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CommentVoteStatus getStatus() {
        return status;
    }

    public void setStatus(CommentVoteStatus status) {
        this.status = status;
    }
}
