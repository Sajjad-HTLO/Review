package com.sajad.demo.converter;

import com.sajad.demo.domain.User;
import com.sajad.demo.domain.Vote;
import com.sajad.demo.dto.VoteNewDto;
import com.sajad.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoteConverters {

    private final UserService userService;

    @Autowired
    public VoteConverters(UserService userService) {
        this.userService = userService;
    }

    public Vote fromNewDto(VoteNewDto newDto) {
        Vote vote = new Vote();

        // An automatic unboxing happens here
        vote.setRate(newDto.getRate());

        Optional<User> userOptional = userService.getById(newDto.getUserId());

        // Should make another decision if the user has been deleted anyway
        userOptional.ifPresent(vote::setUser);

        return vote;
    }
}
