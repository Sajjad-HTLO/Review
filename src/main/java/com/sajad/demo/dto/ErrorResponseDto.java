package com.sajad.demo.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class ErrorResponseDto {

    /**
     * Determines the HTTP status code of the to-be-returned error response. This field only has
     * internal usages and won't be serialized to be part of our JSON output
     */
    @JsonIgnore
    private final HttpStatus httpStatus;

    /**
     * Error content
     */
    private final String content;

    public ErrorResponseDto(HttpStatus httpStatus, String content) {
        this.content = content;
        Assert.notNull(httpStatus, "The http status can't be null");

        this.httpStatus = httpStatus;
    }


    public String getContent() {
        return content;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
