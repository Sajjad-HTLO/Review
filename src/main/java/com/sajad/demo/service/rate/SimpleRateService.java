package com.sajad.demo.service.rate;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.repository.RateRepository;
import com.sajad.demo.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleRateService implements RateService {

    private final RateRepository rateRepository;

    @Autowired
    public SimpleRateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Page<Rate> listRates(Predicate predicate, Pageable pageable) {
        return rateRepository.findAll(predicate, pageable);
    }

    @Override
    public Optional<Rate> getById(long id) {
        return rateRepository.findById(id);
    }

    @Override
    public void updateRateStatus(Rate rate, CommentRateStatus decision) {
        rate.setStatus(decision);
        rateRepository.save(rate);
    }
}
