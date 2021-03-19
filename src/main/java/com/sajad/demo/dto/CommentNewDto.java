package com.sajad.demo.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * For simplicity's sake, some context like user_id and the flag to indicate either the current user
 * is a previous buyer of this product will be provided by the client's payload.
 */
public class CommentNewDto {

    /**
     * The comment content.
     * (We also need to inspect for XSS attack, in future of course!)
     */
    @NotBlank
    private String content;

    @NotNull
    private Long productId;

    /**
     * Since we don't have a security context to be populated upon the authentication, we have to
     * rely on the user identifier passed from the client
     */
    @NotNull
    private Long userId;

    @NotNull
    private Boolean isBuyer;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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
}
