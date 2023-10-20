package com.moses33.habitof6.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    public MockHttpServletRequestBuilder myPost(String urlTemplate, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.post(getApiBasePath() + urlTemplate, uriVariables));
    }

    public MockHttpServletRequestBuilder myGet(String urlTemplate, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.get(getApiBasePath() + urlTemplate, uriVariables));
    }

    private MockHttpServletRequestBuilder setDefaultConfigs(MockHttpServletRequestBuilder mockHttpServletRequestBuilder)
    {
        return mockHttpServletRequestBuilder.accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
    }



    /**
     * this value is prepended to all request
     * @return must return the base path to avoid duplicating string values in code
     */
    public abstract String getApiBasePath();

}
