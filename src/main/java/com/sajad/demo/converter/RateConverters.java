package com.sajad.demo.converter;

import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.rate.RateDto;
import com.sajad.demo.dto.rate.RateNewDto;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RateConverters {

    private final UserService userService;

    @Autowired
    public RateConverters(UserService userService) {
        this.userService = userService;
    }

    public static RateDto fromRate(Rate rate) {
        RateDto dto = new RateDto();

        dto.setId(rate.getId());
        dto.setRate(rate.getValue());
        dto.setStatus(rate.getStatus());
        dto.setDate(rate.getDate());

        return dto;
    }

    public Rate fromNewDto(RateNewDto newDto) {
        Rate rate = new Rate();
        // An automatic unboxing happens here
        rate.setValue(newDto.getRate());

        User user = userService.getById(newDto.getUserId()).orElseThrow(ResourceNotFoundException::new);
        rate.setUser(user);

        return rate;
    }
}
