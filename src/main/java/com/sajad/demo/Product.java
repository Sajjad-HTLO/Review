package com.sajad.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @OneToMany
    @JoinTable(
            name="product_comments",
            joinColumns = @JoinColumn( name="product_id"),
            inverseJoinColumns = @JoinColumn( name="comment_id")
    )
    private Set<Comment> comments;

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
     * If {@code true} then this products' comments will be visible to the public users,
     * Otherwise, only the previous buyers can see the comments.
     */
    private boolean commentsVisibleToPublic;

    /**
     * If {@code true} then this products' votes will be visible to the public users,
     * Otherwise, only the previous buyers can see the votes.
     */
    private boolean votesVisibleToPublic;


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

    public boolean isCommentsVisibleToPublic() {
        return commentsVisibleToPublic;
    }

    public void setCommentsVisibleToPublic(boolean commentsVisibleToPublic) {
        this.commentsVisibleToPublic = commentsVisibleToPublic;
    }

    public boolean isVotesVisibleToPublic() {
        return votesVisibleToPublic;
    }

    public void setVotesVisibleToPublic(boolean votesVisibleToPublic) {
        this.votesVisibleToPublic = votesVisibleToPublic;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
