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

    private final ProductService productService;

    private final RateService rateService;

    private final RateConverters rateConverters;

    @Autowired
    public RateController(ProductService productService, RateService rateService, RateConverters rateConverters) {
        this.productService = productService;
        this.rateService = rateService;
        this.rateConverters = rateConverters;
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
     * Endpoint to intercept a new ate for a product.
     *
     * @param newDto
     * @return
     * @throws RateNotAllowedException The product is not votable.
     */
    @PostMapping
    public ResponseEntity newRate(@Validated @RequestBody RateNewDto newDto) throws RateNotAllowedException {
        Product product = productService.getById(newDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        /*
        Check the rating rules
        I didn't move this checking to voting service layer, because I don't like passing the dto instance to
        the service layer.
        As a predefined rule for repeated rating, we re-write the rate
         */
        if (Utility.isRatingRulesViolated(product, newDto.getIsBuyer()))
            throw new RateNotAllowedException();

        // Check for duplicate rate and update the rate value
        Optional<Rate> duplicate = rateService.getByUserIdAndProductId(newDto.getUserId(), newDto.getProductId());

        if (duplicate.isPresent()) {
            duplicate.ifPresent(updatedRate ->
                    product.getRates().stream()
                            // We're sure there duplicate rate exist
                            .filter(rate -> rate.getId().equals(updatedRate.getId()))
                            .findFirst().get()
                            .setValue(newDto.getRate()));
        } else {
            Rate newRate = rateConverters.fromNewDto(newDto);
            product.getRates().add(newRate);
        }

        productService.persistUpdatedProduct(product);

        return ResponseEntity.noContent().build();
    }

    /**
     * Update (Approve or Reject) a given rate. (By an admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity updateRateStatus(@PathVariable long id, @Validated @RequestBody DecisionDto decisionDto) {
        Rate rate = rateService.getById(id).orElseThrow(ResourceNotFoundException::new);

        rate.setStatus(decisionDto.getDecision());

        rateService.persistNewRate(rate);

        return ResponseEntity.noContent().build();
    }
}
