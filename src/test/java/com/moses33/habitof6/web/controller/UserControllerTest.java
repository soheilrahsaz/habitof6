package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.web.dto.auth.ChangePasswordDto;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerTest extends BaseTest{
    @Override
    public String getApiBasePath() {
        return "/user";
    }

    @Test
    @WithUserDetails(value = username1, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testValidGetUserInfo() throws Exception {

        mockMvc.perform(myGetSimple())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.username", Is.is(username1)));
    }

    @Test
    @WithUserDetails(value = username1, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testUpdateUserInfo() throws Exception {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .username(username1)
                .email("test@test.com")
                .firstName("myFirstName")
                .lastName("myLastName")
                .build();

        mockMvc.perform(myPatchSimple()
                        .content(objectMapper.writeValueAsString(userInfoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.firstName", Is.is(userInfoDto.getFirstName())))
                .andExpect(jsonPath("$.result.lastName", Is.is(userInfoDto.getLastName())));
    }
    @Test
    @WithUserDetails(value = username1)
    void testChangePasswordInvalidValue() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .password("1234")
                .reTypePassword("5678")
                .build();

        mockMvc.perform(myPatch("/password")
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"));
    }
    @Test
    @WithUserDetails(value = username1, setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void testChangePassword() throws Exception {
        ChangePasswordDto changePasswordDto = ChangePasswordDto.builder()
                .password("123456")
                .reTypePassword("123456")
                .build();

        mockMvc.perform(myPatch("/password")
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(status().isOk());
    }
}
