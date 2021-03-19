package com.sajad.demo.converter;

import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.RateNewDto;
import com.sajad.demo.dto.VoteDto;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RateConverters {

    private final UserService userService;

    private final ProductService productService;

    @Autowired
    public RateConverters(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    public static VoteDto fromRate(Rate rate) {
        VoteDto dto = new VoteDto();

        dto.setId(rate.getId());
        dto.setRate(rate.getValue());
        dto.setStatus(rate.getStatus());
        dto.setDate(rate.getDate());

        return dto;
    }

    public Rate fromNewDto(RateNewDto newDto) {
        Product product = productService.getById(newDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        Rate rate = new Rate();
        rate.setProduct(product);

        // An automatic unboxing happens here
        rate.setValue(newDto.getRate());

        Optional<User> userOptional = userService.getById(newDto.getUserId());

        // Should make another decision if the user has been deleted anyway
        userOptional.ifPresent(rate::setUser);

        return rate;
    }
}
