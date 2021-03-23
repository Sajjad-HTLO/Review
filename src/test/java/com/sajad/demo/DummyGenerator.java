package com.sajad.demo;

import com.sajad.demo.domain.*;

import java.util.Date;

/**
 * Responsible for creating dummy objects used in test suites.
 */
public class DummyGenerator {

    public static User getDummyUser(String username) {
        User user = new User();
        user.setId(1L);
        user.setUsername(username);

        return user;
    }

    public static Comment getDummyComment(String content, CommentRateStatus status, User user) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setStatus(status);
        comment.setUser(user);
        comment.setDate(new Date());

        return comment;
    }

    public static Rate getDummyRate(CommentRateStatus status, User user, int value) {
        Rate rate = new Rate();
        rate.setId(2L);
        rate.setUser(user);
        rate.setValue(value);
        rate.setStatus(status);
        rate.setDate(new Date());

        return rate;
    }
}
