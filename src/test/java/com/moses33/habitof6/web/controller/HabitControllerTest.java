package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.web.dto.HabitDirectionDto;
import com.moses33.habitof6.web.dto.HabitDto;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HabitControllerTest extends BaseTest{
    @Override
    public String getApiBasePath() {
        return "/habit";
    }

    @Test
    @WithUserDetails(username1)
    void testAddHabit() throws Exception{
        HabitDto habitDto = HabitDto.builder()
                .name("Eat")
                .colorHex("#658453")
                .direction(HabitDirectionDto.ASC)
                .days("0,1,2,3")
                .build();
        mockMvc.perform(myPostSimple()
                .content(objectMapper.writeValueAsString(habitDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails(username1)
    void testInvalidHabit() throws Exception{
        HabitDto habitDto = HabitDto.builder()
                .name("Eat")
                .colorHex("invalid")
                .direction(HabitDirectionDto.ASC)
                .days("0,2")
                .build();
        mockMvc.perform(myPostSimple()
                        .content(objectMapper.writeValueAsString(habitDto)))
                .andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"))
                .andExpect(jsonPath("$.result.size()", Is.is(1)));
    }

    @Test
    @WithUserDetails(username1)
    void testInvalidDays() throws Exception{
        HabitDto habitDto = HabitDto.builder()
                .name("Eat")
                .colorHex("#123456")
                .direction(HabitDirectionDto.ASC)
                .days("0,2,2,1")
                .build();

        mockMvc.perform(myPostSimple()
                        .content(objectMapper.writeValueAsString(habitDto)))
                .andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"))
                .andExpect(jsonPath("$.result.size()", Is.is(1)));


        habitDto.setDays("123");
        mockMvc.perform(myPostSimple()
                        .content(objectMapper.writeValueAsString(habitDto)))
                .andExpect(status().isBadRequest())
                .andExpect(errorIs("InvalidInput"))
                .andExpect(jsonPath("$.result.size()", Is.is(1)));
    }
}
