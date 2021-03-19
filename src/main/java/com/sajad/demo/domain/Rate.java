package com.sajad.demo.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Encapsulates a rate, the value range is from 1 to 5.
 */
@Entity
@Table(name = "rate")
public class Rate implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Integer value;

    /**
     * Initial status of the rate.
     */
    private CommentRateStatus status = CommentRateStatus.PENDING;

    /**
     * Rating date
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date date = new Date();

    public Long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
