package com.sajad.demo.dto.comment;

import com.sajad.demo.domain.CommentRateStatus;

import java.util.Date;

/**
 * A single verified comment of a product.
 * (The comment owner info skipped)
 */
public class CommentDto {

    private long id;

    private String content;

    private CommentRateStatus status;

    private Date date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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
}
