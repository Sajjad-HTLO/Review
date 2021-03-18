package com.sajad.demo.service.user;

import com.sajad.demo.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getById(long id);
}
