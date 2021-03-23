package com.sajad.demo.unit;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.BooleanBuilder;
import com.sajad.demo.domain.*;
import com.sajad.demo.helper.DummyGenerator;
import com.sajad.demo.repository.ProductRepository;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.product.SimpleProductService;
import com.sajad.demo.service.rate.RateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepositoryMock;

    private Product product;

    private User user;

    private Comment comment;

    private Rate rate;

    @Before
    public void setup() {
        user = DummyGenerator.getDummyUser("user1");
        comment = DummyGenerator.getDummyComment("cn1", CommentRateStatus.VERIFIED, user);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment);

        product = new Product.ProductBuilder()
                .withName("product 1")
                .withComments(comments)
                .withRates(Collections.emptySet())
                .isVisible(true)
                .isCommentable(true)
                .isRatableToPublic(true)
                .isRatableToPublic(true)
                .build();

        rate = DummyGenerator.getDummyRate(product, CommentRateStatus.PENDING, user, 4);
    }

    @Test
    public void listProducts_oneProductExists_ShouldReturnProducts() {
        BooleanBuilder predicate = new BooleanBuilder();
        PageRequest pageRequest = PageRequest.of(0, 10);

        doReturn(new PageImpl<>(ImmutableList.of(product)))
                .when(productRepositoryMock).findAll(eq(predicate), eq(pageRequest));

        Page<Product> products = productService.listProducts(predicate, pageRequest);

        assertThat(products).isNotNull();
        assertThat(products.getContent().size()).isEqualTo(1);
        assertThat(products.getContent().iterator().next()).isEqualTo(product);
        assertThat(products.getContent().iterator().next().getName()).isEqualTo(product.getName());
        assertThat(products.getContent().iterator().next().getRates()).isEmpty();
        assertThat(products.getContent().iterator().next().getComments().size()).isEqualTo(1);
        assertThat(products.getContent().iterator().next().getComments().iterator().next()).isEqualTo(comment);
    }


    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProductService testBeanDefinition(ProductRepository productRepository) {
            return new SimpleProductService(productRepository);
        }
    }
}
