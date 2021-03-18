package com.sajad.demo.controller;

import com.sajad.demo.converter.VoteConverters;
import com.sajad.demo.domain.Product;
import com.sajad.demo.domain.Vote;
import com.sajad.demo.dto.VoteNewDto;
import com.sajad.demo.exception.General4XXException;
import com.sajad.demo.exception.VoteNotAllowedException;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sajad.demo.utility.Constants.VOTES_API;

@RestController
@RequestMapping(VOTES_API)
public class VoteController {

    private final ProductService productService;

    private final VoteService voteService;

    private final VoteConverters voteConverters;

    @Autowired
    public VoteController(ProductService productService, VoteService voteService, VoteConverters voteConverters) {
        this.productService = productService;
        this.voteService = voteService;
        this.voteConverters = voteConverters;
    }

    /**
     * Endpoint to intercept a new vote for a product.
     *
     * @param newDto
     * @return
     * @throws VoteNotAllowedException The product is not votable.
     */
    @PostMapping
    public ResponseEntity newVote(@Validated @RequestBody VoteNewDto newDto) throws VoteNotAllowedException {
        Product product = productService.getById(newDto.getProductId()).orElseThrow(General4XXException::new);

        /*
        Complain if the product is not votable.
        I didn't move this checking to the voting service layer, because I don't like passing the dto instance to
        the service layer.
         */
        if (!product.isVotable())
            throw new VoteNotAllowedException();

        Vote newVote = voteConverters.fromNewDto(newDto);

        // So persist the new vote and return
        voteService.persistNewVote(newVote);

        return ResponseEntity.noContent().build();
    }
}
