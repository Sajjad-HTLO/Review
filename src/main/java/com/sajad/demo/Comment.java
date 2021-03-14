package com.sajad.demo;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String text) {
        this.content = text;
    }
}
