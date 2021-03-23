package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.RateConverters;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.dto.DecisionDto;
import com.sajad.demo.dto.rate.RateNewDto;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.exception.RateNotAllowedException;
import com.sajad.demo.helper.Utility;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.rate.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.sajad.demo.helper.Constants.UrlMappings.RATES_API;

@RestController
@RequestMapping(RATES_API)
public class RateController {

    private final RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Endpoint to list rates and filter them on demand.
     * The admin can see non-verified rates too. (needs checking role of the principal)
     *
     * @param predicate
     * @return
     */
    @GetMapping
    public ResponseEntity listRates(@QuerydslPredicate(root = Rate.class) Predicate predicate,
                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
                rateService.listRates(predicate, pageable)
                        .stream()
                        .map(RateConverters::fromRate));
    }

    /**
     * Update (Approve or Reject) a given rate. (By an admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity updateRateStatus(@PathVariable long id, @Validated @RequestBody DecisionDto decisionDto) {
        Rate rate = rateService.getById(id).orElseThrow(ResourceNotFoundException::new);
        rateService.updateRateStatus(rate, decisionDto.getDecision());

        return ResponseEntity.noContent().build();
    }
}
