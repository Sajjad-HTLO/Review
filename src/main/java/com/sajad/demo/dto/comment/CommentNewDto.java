package com.sajad.demo.dto.comment;

import com.sajad.demo.dto.ContextDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * For simplicity's sake, some context like user_id and the flag to indicate either the current user
 * is a previous buyer of this product will be provided by the client's payload.
 */
public class CommentNewDto extends ContextDto {

    /**
     * The comment content.
     * (We also need to inspect for XSS attack, in future of course!)
     */
    @NotBlank
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
