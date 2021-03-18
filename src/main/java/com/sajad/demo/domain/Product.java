package com.sajad.demo.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "product_comments",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id")
    )
    private Set<Comment> comments;

    @OneToMany
    @JoinTable(
            name = "product_votes",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "vote_id")
    )
    private Set<Vote> votes;

    /**
     * The product name
     */
    private String name;

    /**
     * Either this product is visible to the customers to order.
     */
    private boolean isVisible;

    /**
     * Either this is allowed for this product to write comments.
     */
    private boolean isCommentable;

    /**
     * Either this is allowed for this product to vote.
     */
    private boolean isVotable;

    /**
     * If {@code true} then every user can put comment for this product.
     * Otherwise, only the previous buyers can put comments.
     */
    private boolean commentableToPublic;

    /**
     * If {@code true} then every user can rate for this product.
     * Otherwise, only the previous buyers can rate.
     */
    private boolean votableToPublic;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public boolean isCommentable() {
        return isCommentable;
    }

    public void setCommentable(boolean commentable) {
        isCommentable = commentable;
    }

    public boolean isVotable() {
        return isVotable;
    }

    public void setVotable(boolean votable) {
        isVotable = votable;
    }

    public boolean isCommentableToPublic() {
        return commentableToPublic;
    }

    public void setCommentableToPublic(boolean commentsVisibleToPublic) {
        this.commentableToPublic = commentsVisibleToPublic;
    }

    public boolean isVotableToPublic() {
        return votableToPublic;
    }

    public void setVotableToPublic(boolean votesVisibleToPublic) {
        this.votableToPublic = votesVisibleToPublic;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
