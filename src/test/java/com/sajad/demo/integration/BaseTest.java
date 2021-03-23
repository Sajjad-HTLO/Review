package com.sajad.demo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class BaseTest {

    @Autowired
    public ObjectMapper objectMapper;

    /**
     * Spring Mock MVC
     */
    @Autowired
    protected MockMvc mockMvc;
}
