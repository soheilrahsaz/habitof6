package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.web.dto.auth.ChangePasswordDto;
import com.moses33.habitof6.web.dto.auth.LoginDto;
import com.moses33.habitof6.web.dto.auth.RegisterUserDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

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

    @Test
    @WithUserDetails(value = username1)
    void testChangePasswordInvalidValue() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .password("1234")
                .reTypePassword("5678")
                .build();

        mockMvc.perform(myPost("/changePassword")
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"));
    }
}
