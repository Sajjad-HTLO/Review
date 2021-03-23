package com.sajad.demo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.Predicate;
import com.sajad.demo.DummyGenerator;
import com.sajad.demo.controller.ProductController;
import com.sajad.demo.converter.CommentConverters;
import com.sajad.demo.converter.ProductConverters;
import com.sajad.demo.converter.RateConverters;
import com.sajad.demo.domain.*;
import com.sajad.demo.dto.DecisionDto;
import com.sajad.demo.dto.comment.CommentNewDto;
import com.sajad.demo.dto.product.ProductUpdateDto;
import com.sajad.demo.dto.rate.RateNewDto;
import com.sajad.demo.repository.ProductRepository;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.product.SimpleProductService;
import com.sajad.demo.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;

import java.util.*;

import static com.sajad.demo.helper.Constants.ErrorMessages.COMMENT_NOT_ALLOWED_MSG;
import static com.sajad.demo.helper.Constants.ErrorMessages.RATE_NOT_ALLOWED_MSG;
import static com.sajad.demo.helper.Constants.UrlMappings.PRODUCT_API;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductController.class)
@EnableSpringDataWebSupport
@Import(value = {ProductConverters.class, CommentConverters.class, RateConverters.class})
@RunWith(SpringRunner.class)
public class ProductControllerTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepositoryMock;

    @MockBean
    private UserService userServiceMock;

    private Product dummyProduct;

    private User user = DummyGenerator.getDummyUser("user1");

    @Before
    public void setup() {
        User user = DummyGenerator.getDummyUser("user1");
        Comment comment = DummyGenerator.getDummyComment("cn1", CommentRateStatus.VERIFIED, user);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment);

        Rate rate = DummyGenerator.getDummyRate(CommentRateStatus.PENDING, user, 4);
        Set<Rate> rates = new HashSet<>();
        rates.add(rate);

        dummyProduct = new Product.ProductBuilder()
                .withId(2L)
                .withName("product 1")
                .withComments(comments)
                .withRates(rates)
                .isVisible(true)
                .isCommentable(true)
                .isRatable(true)
                .isRatableToPublic(true)
                .build();
    }

    @Test
    public void getProductsList_HaseOnProduct_ShouldReturnList() throws Exception {
        doReturn(new PageImpl<>(ImmutableList.of(dummyProduct)))
                .when(productRepositoryMock).findAll(any(Predicate.class), any(Pageable.class));

        mockMvc.perform(get(PRODUCT_API)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].name", is(dummyProduct.getName())))
                .andExpect(jsonPath("$.[0].visible", is(dummyProduct.isVisible())))
                .andExpect(jsonPath("$.[0].ratable", is(dummyProduct.isRatable())))
                .andExpect(jsonPath("$.[0].commentable", is(dummyProduct.isCommentable())))
                .andExpect(jsonPath("$.[0].commentable_to_public", is(dummyProduct.isCommentableToPublic())))
                .andExpect(jsonPath("$.[0].ratable_to_public", is(dummyProduct.isRatableToPublic())))
                .andExpect(jsonPath("$.[0].comments", hasSize(dummyProduct.getComments().size())))
                .andExpect(jsonPath("$.[0].comments_count", is(dummyProduct.getComments().size())))
                .andExpect(jsonPath("$.[0].rates_average", is(0.0)));
    }

    @Test
    public void editProductState_LegalCondition_ShouldUpdateSuccessfully() throws Exception {
        doReturn(Optional.of(dummyProduct)).when(productRepositoryMock).findById(12L);

        mockMvc.perform(put(PRODUCT_API + "/" + 12)
                .contentType(MediaType.APPLICATION_JSON)
                .content(makeAllStatesTrue()))
                .andExpect(status().isNoContent());
    }

    @Test
    public void newComment_LegalCondition_ShouldBeSucceed() throws Exception {
        doReturn(Optional.of(user)).when(userServiceMock).getById(1L);
        doReturn(Optional.of(dummyProduct)).when(productRepositoryMock).findById(12L);

        mockMvc.perform(post(PRODUCT_API + "/12/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentDto(true)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void newComment_NotAllowed_ShouldReturnError() throws Exception {
        doReturn(Optional.of(user)).when(userServiceMock).getById(1L);
        doReturn(Optional.of(dummyProduct)).when(productRepositoryMock).findById(12L);

        mockMvc.perform(post(PRODUCT_API + "/12/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newCommentDto(false)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content", is(COMMENT_NOT_ALLOWED_MSG)));
    }

    @Test
    public void newRate_LegalCondition_ShouldBeSucceed() throws Exception {
        // This is a different user rating on this product
        ReflectionTestUtils.setField(user, "id", 15L);
        doReturn(Optional.of(user)).when(userServiceMock).getById(15L);
        doReturn(Optional.of(dummyProduct)).when(productRepositoryMock).findById(1L);

        mockMvc.perform(post(PRODUCT_API + "/1/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRateDto(true)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void newRate_NotRatable_ShouldReturnError() throws Exception {
        // Product is not ratable
        dummyProduct.setRatable(false);
        doReturn(Optional.of(user)).when(userServiceMock).getById(15L);
        doReturn(Optional.of(dummyProduct)).when(productRepositoryMock).findById(1L);

        mockMvc.perform(post(PRODUCT_API + "/1/rates")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newRateDto(false)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.content", is(RATE_NOT_ALLOWED_MSG)));
    }

    private String newRateDto(boolean isBuyer) throws JsonProcessingException {
        RateNewDto newDto = new RateNewDto();

        newDto.setRate(2);
        newDto.setIsBuyer(isBuyer);
        newDto.setUserId(15L);

        return objectMapper.writeValueAsString(newDto);
    }

    private String makeAllStatesTrue() throws JsonProcessingException {
        ProductUpdateDto updateDto = new ProductUpdateDto();

        updateDto.setVisible(true);
        updateDto.setCommentableToPublic(true);
        updateDto.setCommentableToBuyers(true);
        updateDto.setRatableToPublic(true);
        updateDto.setRatableToBuyers(true);

        return objectMapper.writeValueAsString(updateDto);
    }

    private String newCommentDto(boolean isBuyer) throws JsonProcessingException {
        CommentNewDto newDto = new CommentNewDto();

        newDto.setContent("content");
        newDto.setUserId(1L);
        newDto.setIsBuyer(isBuyer);

        return objectMapper.writeValueAsString(newDto);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProductService testBeanDefinition(ProductRepository productRepository) {
            return new SimpleProductService(productRepository);
        }
    }
}
