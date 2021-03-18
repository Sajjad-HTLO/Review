package com.sajad.demo.dto;

/**
 * I considered a class for error response to encapsulate other optional attribute as well.
 */
public class ErrorResponseDto {

    /**
     * Error content
     */
    private final String content;

    public ErrorResponseDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
