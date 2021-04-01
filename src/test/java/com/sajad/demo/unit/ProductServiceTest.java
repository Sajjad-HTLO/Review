package com.sajad.demo.unit;

import com.google.common.collect.ImmutableList;
import com.querydsl.core.BooleanBuilder;
import com.sajad.demo.DummyGenerator;
import com.sajad.demo.domain.*;
import com.sajad.demo.exception.CommentNotAllowedException;
import com.sajad.demo.exception.RateNotAllowedException;
import com.sajad.demo.repository.ProductRepository;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.product.SimpleProductService;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@MockBean(value = {KafkaTemplate.class})
@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepositoryMock;

    private Product product;

    private Comment comment;

    private Rate rate;

    @Before
    public void setup() {
        User user = DummyGenerator.getDummyUser("user1");
        comment = DummyGenerator.getDummyComment("cn1", CommentRateStatus.VERIFIED, user);

        Set<Comment> comments = new HashSet<>();
        comments.add(comment);

        rate = DummyGenerator.getDummyRate(CommentRateStatus.PENDING, user, 4);
        Set<Rate> rates = new HashSet<>();
        rates.add(rate);

        product = new Product.ProductBuilder()
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
        assertThat(products.getContent().iterator().next().getComments().size()).isEqualTo(1);
        assertThat(products.getContent().iterator().next().getComments().iterator().next()).isEqualTo(comment);
        assertThat(products.getContent().iterator().next().getRates().size()).isEqualTo(1);
        assertThat(products.getContent().iterator().next().getRates().iterator().next()).isEqualTo(rate);
    }

    @Test
    public void addComment_OnCommentableProduct_ShouldBeSucceed() throws CommentNotAllowedException {
        productService.newComment(product, comment, true);

        verify(productRepositoryMock, times(1)).save(product);
        verifyNoMoreInteractions(productRepositoryMock);
    }

    @Test
    public void addComment_NotCommentableProduct_ShouldNotAllowed() throws CommentNotAllowedException {
        product.setCommentable(false);

        assertThatExceptionOfType(CommentNotAllowedException.class)
                .isThrownBy(() -> productService.newComment(product, comment, true))
                .as("Can't put comment on this product");

        verifyNoInteractions(productRepositoryMock);
    }

    @Test
    public void rateProduct_OnRatableProduct_ShouldBeSucceed() throws RateNotAllowedException {
        productService.newRate(product, rate, true);

        verify(productRepositoryMock, times(1)).save(product);
        verifyNoMoreInteractions(productRepositoryMock);
    }

    @Test
    public void rateProduct_NotRatableProduct_ShouldNotAllowed() {
        product.setRatable(false);

        assertThatExceptionOfType(RateNotAllowedException.class)
                .isThrownBy(() -> productService.newRate(product, rate, true))
                .as("Can't rate to this product");

        verifyNoInteractions(productRepositoryMock);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ProductService testBeanDefinition(ProductRepository productRepository, KafkaTemplate kafkaTemplate) {
            return new SimpleProductService(productRepository, kafkaTemplate);
        }
    }
}
