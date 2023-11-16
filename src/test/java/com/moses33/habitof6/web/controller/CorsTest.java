package com.moses33.habitof6.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CorsTest extends BaseTest{
    @Override
    public String getApiBasePath() {
        return "";//don't need default prefix in this text
    }

    /**
     * since this project is totally for tests and learning, we want cors enabled at all times
     */
    @Test
    void testEnabledCors() throws Exception {
        mockMvc.perform(options(BaseController.API_BASE_PATH+"/auth/login")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "someRandomOrigin.com"))
                .andExpect(status().isOk());
    }
}
