package com.sajad.demo.dto;

/**
 * A single verified comment of a product.
 * (The comment owner info skipped)
 */
public class CommentDto {

    private String content;

    // We may need dtate time as well

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
