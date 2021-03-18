package com.sajad.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    /**
     * Initial status of the comment.
     */
    private CommentVoteStatus status = CommentVoteStatus.PENDING;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public CommentVoteStatus getStatus() {
        return status;
    }

    public void setStatus(CommentVoteStatus status) {
        this.status = status;
    }
}
