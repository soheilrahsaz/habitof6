package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.domain.security.LoginFailure;
import com.moses33.habitof6.repository.UserRepository;
import com.moses33.habitof6.repository.security.LoginFailureRepository;
import com.moses33.habitof6.repository.security.RoleRepository;
import com.moses33.habitof6.web.dto.auth.LoginDto;
import com.moses33.habitof6.web.dto.auth.RegisterUserDto;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Commit;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SecurityTest extends BaseTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LoginFailureRepository loginFailureRepository;

    final String password1 = "123456";
    final String email1 = "test@test.com";

    @Override
    public String getApiBasePath() {
        return "/auth";
    }

    @Commit
    @BeforeEach
    void setupUser() {
        if (userRepository.findWithRolesByUsername(username1).isEmpty()) {
            userRepository.saveAndFlush(User.builder()
                    .username(username1)
                    .email(email1)
                    .role(roleRepository.findByName("ROLE_USER").orElseThrow())
                    .password(new BCryptPasswordEncoder().encode(password1))
                    .build());
        }
    }

    @Test
    void testInvalidLogin() throws Exception {
        LoginDto loginDto = new LoginDto("ahmad", "1234");
        mockMvc.perform(myPost("/login")
                .content(objectMapper.writeValueAsString(loginDto))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    void testValidLogin() throws Exception {
        LoginDto loginDto = new LoginDto(username1, password1);
        mockMvc.perform(myPost("/login")
                .content(objectMapper.writeValueAsString(loginDto))
        ).andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Commit
    void testValidRegister() throws Exception {
        RegisterUserDto registerUserDto = new RegisterUserDto(RandomStringUtils.randomAlphabetic(32), RandomStringUtils.randomAlphabetic(32),
                RandomStringUtils.randomAlphabetic(32) + "@testmail.com");
        mockMvc.perform(myPost("/register")
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isOk());
        userRepository.deleteByUsername(registerUserDto.getUsername());
    }

    @Test
    void testDuplicateUsernameRegister() throws Exception {

        RegisterUserDto registerUserDto = new RegisterUserDto(username1, RandomStringUtils.randomAlphabetic(32),
                RandomStringUtils.randomAlphabetic(32) + "@testmail.com");
        
        mockMvc.perform(myPost("/register")
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", org.hamcrest.core.StringContains.containsString("Username")));
    }
    
    @Test
    void testDuplicateEmailRegister() throws Exception {
        
        RegisterUserDto registerUserDto = new RegisterUserDto(RandomStringUtils.randomAlphabetic(32), RandomStringUtils.randomAlphabetic(32),
                 email1);
        
        mockMvc.perform(myPost("/register")
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", org.hamcrest.core.StringContains.containsString("Email")));
    }

    @Test
    void invalidGetUserInfo() throws Exception {
        mockMvc.perform(myGet("/getUserInfo"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = username1, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testValidGetUserInfo() throws Exception {

        mockMvc.perform(myGet("/getUserInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.username", Is.is(username1)));
    }

    @Test
    @WithUserDetails(value = username1, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testLogout() throws Exception {
        mockMvc.perform(myGet("/logout"))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testLoginFailureTracking() throws Exception{
        final String testIp = "166.166.166.166";
        loginFailureRepository.deleteBySourceIp(testIp);
        loginFailureRepository.flush();
        LoginDto loginDto = new LoginDto("invalid", "user");
        mockMvc.perform(myPost("/login")
                        .with(request -> {
                            request.setRemoteAddr(testIp);
                            return  request;
                        })
                .content(objectMapper.writeValueAsString(loginDto)));
        LoginFailure loginFailure = loginFailureRepository.findBySourceIp(testIp).orElseThrow();
        Assertions.assertEquals(1, loginFailure.getWrongAttempts());
    }
}
