package com.sajad.demo.dto;

import com.sajad.demo.domain.CommentRateStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RateNewDto {

    /**
     * Since we don't have a security context to be populated upon the authentication, we have to
     * rely on the user identifier passed from the client
     */
    @NotNull
    private Long userId;

    @NotNull
    private Boolean isBuyer;

    @NotNull
    private Long productId;

    /**
     * Initial status of the vote.
     */
    private CommentRateStatus status = CommentRateStatus.PENDING;

    /**
     * The actual rate value ranging from 1 to 5.
     */
    @NotNull
    @Max(value = 5, message = "max is 5")
    @Min(1)
    private Integer rate;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public CommentRateStatus getStatus() {
        return status;
    }

    public void setStatus(CommentRateStatus status) {
        this.status = status;
    }

    public Boolean getIsBuyer() {
        return isBuyer;
    }

    public void setIsBuyer(Boolean isBuyer) {
        this.isBuyer = isBuyer;
    }
}
