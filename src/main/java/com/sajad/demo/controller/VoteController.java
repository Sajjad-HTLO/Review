package com.sajad.demo.controller;

import com.querydsl.core.types.Predicate;
import com.sajad.demo.converter.RateConverters;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.dto.DecisionDto;
import com.sajad.demo.dto.RateNewDto;
import com.sajad.demo.exception.ResourceNotFoundException;
import com.sajad.demo.exception.VoteNotAllowedException;
import com.sajad.demo.helper.Utility;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.vote.VoteService;
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
public class VoteController {

    private final ProductService productService;

    private final VoteService voteService;

    private final RateConverters rateConverters;

    @Autowired
    public VoteController(ProductService productService, VoteService voteService, RateConverters rateConverters) {
        this.productService = productService;
        this.voteService = voteService;
        this.rateConverters = rateConverters;
    }

    /**
     * Endpoint to list votes and filter them on demand.
     * The admin can see non-verified votes too. (needs checking role of the principal)
     *
     * @param predicate
     * @return
     */
    @GetMapping
    public ResponseEntity listVotes(@QuerydslPredicate(root = Rate.class) Predicate predicate,
                                    @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(
                voteService.listVotes(predicate, pageable)
                        .stream()
                        .map(RateConverters::fromRate));
    }

    /**
     * Endpoint to intercept a new vote for a product.
     *
     * @param newDto
     * @return
     * @throws VoteNotAllowedException The product is not votable.
     */
    @PostMapping
    public ResponseEntity newVote(@Validated @RequestBody RateNewDto newDto) throws VoteNotAllowedException {
        Product product = productService.getById(newDto.getProductId()).orElseThrow(ResourceNotFoundException::new);

        /*
        Check the rating rules
        I didn't move this checking to voting service layer, because I don't like passing the dto instance to
        the service layer.
        As a predefined rule for repeated rating, we re-write the vote
         */
        if (Utility.isRatingRulesViolated(product, newDto.getIsBuyer()))
            throw new VoteNotAllowedException();

        // Check for duplicate rate and update the rate value
        Optional<Rate> duplicate = voteService.getByUserIdAndProductId(newDto.getUserId(), newDto.getProductId());

        if (duplicate.isPresent()) {
            duplicate.ifPresent(updatedRate ->
                    product.getRates().stream()
                            // We're sure there duplicate rate exist
                            .filter(vote -> vote.getId().equals(updatedRate.getId()))
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
     * Update (Approve or Reject) a given vote. (By an admin)
     */
    @PutMapping("/{id}")
    public ResponseEntity updateVoteStatus(@PathVariable long id, @Validated @RequestBody DecisionDto decisionDto) {
        Rate rate = voteService.getById(id).orElseThrow(ResourceNotFoundException::new);

        rate.setStatus(decisionDto.getDecision());

        voteService.persistNewVote(rate);

        return ResponseEntity.noContent().build();
    }
}
