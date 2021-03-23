package com.sajad.demo.helper;

import com.sajad.demo.domain.*;

import java.util.Date;

public class DummyGenerator {

    public static User getDummyUser(String username) {
        User user = new User();
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

    public static Rate getDummyRate(Product product, CommentRateStatus status, User user, int value) {
        Rate rate = new Rate();
//        rate.setProduct(product);
        rate.setUser(user);
        rate.setValue(value);
        rate.setStatus(status);
        rate.setDate(new Date());

        return rate;
    }
}
