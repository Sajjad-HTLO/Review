package com.sajad.demo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.querydsl.core.types.Predicate;
import com.sajad.demo.DummyGenerator;
import com.sajad.demo.controller.RateController;
import com.sajad.demo.converter.ProductConverters;
import com.sajad.demo.converter.RateConverters;
import com.sajad.demo.domain.CommentRateStatus;
import com.sajad.demo.domain.Rate;
import com.sajad.demo.domain.User;
import com.sajad.demo.dto.DecisionDto;
import com.sajad.demo.repository.RateRepository;
import com.sajad.demo.service.product.ProductService;
import com.sajad.demo.service.rate.RateService;
import com.sajad.demo.service.rate.SimpleRateService;
import com.sajad.demo.service.user.UserService;
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

import java.util.Optional;

import static com.sajad.demo.helper.Constants.UrlMappings.RATES_API;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = RateController.class)
@EnableSpringDataWebSupport
@Import(value = {ProductConverters.class, RateConverters.class})
@MockBean(classes = {ProductService.class, UserService.class})
@RunWith(SpringRunner.class)
public class RateControllerTest extends BaseTest {

    private final User user = DummyGenerator.getDummyUser("user1");
    private final Rate dummyRate = DummyGenerator.getDummyRate(CommentRateStatus.VERIFIED, user, 4);

    @MockBean
    private RateRepository rateRepositoryMock;

    @Autowired
    private RateService rateService;

    @Test
    public void getRatesList_HasOneRate_ShouldReturnList() throws Exception {
        doReturn(new PageImpl<>(ImmutableList.of(dummyRate)))
                .when(rateRepositoryMock).findAll(any(Predicate.class), any(Pageable.class));

        mockMvc.perform(get(RATES_API)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$.[0].id", is(2)))
                .andExpect(jsonPath("$.[0].rate", is(dummyRate.getValue())))
                .andExpect(jsonPath("$.[0].status", is(dummyRate.getStatus().toString())))
                .andExpect(jsonPath("$.[0].date").exists());
    }

    @Test
    public void approveOrRejectRate_ApproveRate_ShouldBeSuccessful() throws Exception {
        doReturn(Optional.of(dummyRate)).when(rateRepositoryMock).findById(1L);

        mockMvc.perform(put(RATES_API + "/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getVerifyDto()))
                .andExpect(status().isNoContent());
    }

    private String getVerifyDto() throws JsonProcessingException {
        DecisionDto dto = new DecisionDto();
        dto.setDecision(CommentRateStatus.VERIFIED);

        return objectMapper.writeValueAsString(dto);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RateService testBeanDefinition(RateRepository repository) {
            return new SimpleRateService(repository);
        }
    }
}
