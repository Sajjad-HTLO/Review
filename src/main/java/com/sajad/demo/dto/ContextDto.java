package com.sajad.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import javax.validation.constraints.NotNull;

public class ContextDto {

    /**
     * Since we don't have a security context to be populated upon the authentication, we have to
     * rely on the user identifier passed from the client
     */
    @NotNull
    @JsonAlias("user_id")
    private Long userId;

    @JsonAlias("is_buyer")
    private Boolean isBuyer;

    @NotNull
    @JsonAlias("product_id")
    private Long productId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getIsBuyer() {
        return isBuyer;
    }

    public void setIsBuyer(Boolean isBuyer) {
        this.isBuyer = isBuyer;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
