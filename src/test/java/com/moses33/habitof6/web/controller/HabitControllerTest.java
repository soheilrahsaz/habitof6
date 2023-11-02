package com.moses33.habitof6.web.controller;

import com.jayway.jsonpath.JsonPath;
import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.repository.HabitRepository;
import com.moses33.habitof6.repository.UserRepository;
import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDirectionDto;
import com.moses33.habitof6.web.dto.habit.UpdateHabitDto;
import com.moses33.habitof6.web.mapper.HabitMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HabitControllerTest extends BaseTest{

    @Autowired
    HabitMapper habitMapper;

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getApiBasePath() {
        return "/habit";
    }

    public Habit getTestHabit()
    {
        Habit habit = Habit.builder()
                .name("TEST_HABIT__")
                .colorHex("#658453")
                .direction(HabitDirection.ASC)
                .days("0,1,2,3")
                .build();
        return habitRepository.findByName(habit.getName())
                .orElseGet(() -> {
                    habit.setUser(userRepository.findWithRolesByUsername(username1).orElseThrow());
                    return habitRepository.save(habit);
                });
    }

    @Test
    @WithUserDetails(username1)
    void testAddHabit() throws Exception{
        CreateHabitDto habitDto = CreateHabitDto.builder()
                .name("Eat")
                .colorHex("#658453")
                .direction(HabitDirectionDto.ASC)
                .days("0,1,2,3")
                .build();
        String contentAsString = mockMvc.perform(myPostSimple()
                        .content(objectMapper.writeValueAsString(habitDto)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        Integer habitId = JsonPath.read(contentAsString, "$.result.id");
        habitRepository.deleteById(habitId);
    }

    @Test
    @WithUserDetails(username1)
    void testInvalidHabit() throws Exception{
        CreateHabitDto habitDto = CreateHabitDto.builder()
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
        CreateHabitDto habitDto = CreateHabitDto.builder()
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

    @Test
    @WithUserDetails(username1)
    void testGetHabits() throws Exception{
        mockMvc.perform(myGetSimple()
                .param("pageNumber", "0")
                .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content.size()", lessThanOrEqualTo(5)));
    }

    @Test
    @WithUserDetails(username1)
    void testUpdateHabit() throws Exception {
        Habit habit = getTestHabit();
        UpdateHabitDto updateHabitDto = habitMapper.habitToUpdateHabitDto(habit);
        updateHabitDto.setDays("0");
        mockMvc.perform(myPut("/{habitId}", habit.getId())
                .content(objectMapper.writeValueAsString(updateHabitDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.days", Is.is("0")));
    }

    @Test
    @WithUserDetails(username1)
    void testInvalidVersionWhileUpdatingHabit() throws Exception{
        Habit habit = getTestHabit();
        UpdateHabitDto updateHabitDto = habitMapper.habitToUpdateHabitDto(habit);

        habit.setColorHex("#"+ RandomStringUtils.randomNumeric(6));
        habitRepository.save(habit);//version is updated

        updateHabitDto.setColorHex("#"+ RandomStringUtils.randomNumeric(6));
        mockMvc.perform(myPut("/{habitId}", habit.getId())
                        .content(objectMapper.writeValueAsString(updateHabitDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(username1)
    void testDeleteHabit() throws Exception {
        Habit habit = getTestHabit();

        mockMvc.perform(myDelete("/{habitId}", habit.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", Is.is(Boolean.TRUE)));
    }
}
