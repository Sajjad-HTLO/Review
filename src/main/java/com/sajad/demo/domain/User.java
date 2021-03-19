package com.sajad.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A user entity, could be a normal user, or an admin (based on the role)
 * (Additional attributes skipped)
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String username;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
