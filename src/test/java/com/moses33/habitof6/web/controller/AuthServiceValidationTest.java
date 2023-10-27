package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.web.dto.LoginDto;
import com.moses33.habitof6.web.dto.RegisterUserDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AuthServiceValidationTest extends BaseTest{
    @Override
    public String getApiBasePath() {
        return "/auth";
    }

    @Test
    void testInvalidLoginInput() throws Exception{
        //both username and password are invalid
        LoginDto loginDto = new LoginDto(RandomStringUtils.randomAlphabetic(33),
                RandomStringUtils.randomAlphabetic(33));

        mockMvc.perform(myPost("/login")
                        .content(objectMapper.writeValueAsString(loginDto))
                ).andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"))
                .andExpect(jsonPath("$.result.size()", Is.is(2)));
    }

    @Test
    void testInvalidRegisterInput() throws Exception{
        //username and password and email are invalid, email is invalid for 2 reasons
        RegisterUserDto registerUserDto = new RegisterUserDto(RandomStringUtils.randomAlphabetic(33),
                RandomStringUtils.randomAlphabetic(33),
                RandomStringUtils.randomAlphabetic(256));

        mockMvc.perform(myPost("/register")
                        .content(objectMapper.writeValueAsString(registerUserDto))
                ).andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"))
                .andExpect(jsonPath("$.result.size()", Is.is(3)));
    }
}
