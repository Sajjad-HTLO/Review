package com.sajad.demo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product implements Serializable {

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

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "product_rates",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id")
    )
    private Set<Rate> rates;

    /**
     * The product name
     */
    private String name;

    /**
     * Either this product is visible to the customers to order.
     */
    private boolean visible;

    /**
     * Either this is allowed for this product to write comments.
     */
    private boolean isCommentable;

    /**
     * Either this is allowed for this product to rate.
     */
    private boolean isRatable;

    /**
     * If {@code true} then every user can put comment for this product.
     * Otherwise, only the previous buyers can put comments.
     */
    private boolean commentableToPublic;

    /**
     * If {@code true} then every user can rate for this product.
     * Otherwise, only the previous buyers can rate.
     */
    private boolean RatableToPublic;

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
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isCommentable() {
        return isCommentable;
    }

    public void setCommentable(boolean commentable) {
        isCommentable = commentable;
    }

    public boolean isRatable() {
        return isRatable;
    }

    public void setRatable(boolean ratable) {
        isRatable = ratable;
    }

    public boolean isCommentableToPublic() {
        return commentableToPublic;
    }

    public void setCommentableToPublic(boolean commentsVisibleToPublic) {
        this.commentableToPublic = commentsVisibleToPublic;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Rate> getRates() {
        return rates;
    }

    public void setRates(Set<Rate> rates) {
        this.rates = rates;
    }

    public boolean isRatableToPublic() {
        return RatableToPublic;
    }

    public void setRatableToPublic(boolean ratableToPublic) {
        RatableToPublic = ratableToPublic;
    }
}
