package com.sajad.demo.dto;

import com.sajad.demo.domain.CommentRateStatus;

import java.util.Date;

/**
 * Represent a vote to the client.
 */
public class VoteDto {

    private long id;

    private int rate;

    private CommentRateStatus status;

    private Date date;

    public CommentRateStatus getStatus() {
        return status;
    }

    public void setStatus(CommentRateStatus status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}
