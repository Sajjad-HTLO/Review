package com.sajad.demo.service.rate;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.Rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RateService {

    Page<Rate> listRates(Predicate predicate, Pageable pageable);

    Optional<Rate> getById(long id);

    void updateRateStatus(Rate rate, CommentRateStatus decision);
}
