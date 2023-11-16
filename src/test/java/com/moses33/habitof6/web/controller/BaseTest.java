package com.moses33.habitof6.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public abstract class BaseTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;
    protected MockMvc mockMvc;

    final String username1 = "username1__test";
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    public MockHttpServletRequestBuilder myPatchSimple(Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.patch(getFullApiBasePath(), uriVariables));
    }
    public MockHttpServletRequestBuilder myPatch(String extraPart, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.patch(getFullApiBasePath() + extraPart, uriVariables));
    }

    public MockHttpServletRequestBuilder myPostSimple(Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.post(getFullApiBasePath(), uriVariables));
    }
    public MockHttpServletRequestBuilder myPost(String extraPart, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.post(getFullApiBasePath() + extraPart, uriVariables));
    }
    public MockHttpServletRequestBuilder myPut(String extraPart, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.put(getFullApiBasePath() + extraPart, uriVariables));
    }
    public MockHttpServletRequestBuilder myDelete(String extraPart, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.delete(getFullApiBasePath() + extraPart, uriVariables));
    }

    public MockHttpServletRequestBuilder myGetSimple(Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.get(getFullApiBasePath(), uriVariables));
    }

    public MockHttpServletRequestBuilder myGet(String urlTemplate, Object... uriVariables)
    {
        return setDefaultConfigs(MockMvcRequestBuilders.get(getFullApiBasePath() + urlTemplate, uriVariables));
    }

    private MockHttpServletRequestBuilder setDefaultConfigs(MockHttpServletRequestBuilder mockHttpServletRequestBuilder)
    {
        return mockHttpServletRequestBuilder.accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON);
    }
    protected ResultMatcher errorIs(String substr)
    {
        return jsonPath("$.error", Is.is(substr));
    }

    private String getFullApiBasePath()
    {
        return BaseController.API_BASE_PATH+getApiBasePath();
    }


    /**
     * this value is prepended to all request
     * @return must return the base path to avoid duplicating string values in code
     */
    public abstract String getApiBasePath();

}
