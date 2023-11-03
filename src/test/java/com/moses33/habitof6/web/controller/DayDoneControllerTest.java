package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.domain.DayDone;
import com.moses33.habitof6.domain.DayDoneType;
import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.repository.DayDoneRepository;
import com.moses33.habitof6.repository.HabitRepository;
import com.moses33.habitof6.repository.UserRepository;
import com.moses33.habitof6.web.mapper.DayDoneMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class DayDoneControllerTest extends BaseTest {

    @Autowired
    DayDoneMapper dayDoneMapper;

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    DayDoneRepository dayDoneRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String getApiBasePath() {
        return "/habit/{habitId}/dayDone";
    }

    public Habit getTestHabit() {
        Habit habit = Habit.builder()
                .name("TEST_HABIT__WITH_DAY_DONE")
                .colorHex("#658453")
                .direction(HabitDirection.ASC)
                .days("0,1,2,3")
                .build();
        return habitRepository.findByName(habit.getName())
                .orElseGet(() -> {
                    habit.setUser(userRepository.findWithRolesByUsername(username1).orElseThrow());
                    Habit savedHabit = habitRepository.save(habit);
                    dayDoneRepository.saveAll(List.of
                            (DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(20)).type(DayDoneType.DONE).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(8)).type(DayDoneType.DONE).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(7)).type(DayDoneType.DONE).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(6)).type(DayDoneType.SKIP).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(5)).type(DayDoneType.DONE).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(4)).type(DayDoneType.DONE).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(3)).type(DayDoneType.SKIP).build(),
                            DayDone.builder().habit(savedHabit).date(LocalDate.now().minusDays(2)).type(DayDoneType.DONE).build()));
                    return savedHabit;
                });
    }

    @Test
    @WithUserDetails(username1)
    void testGetHabits() throws Exception {
        Habit habit = getTestHabit();
        Integer habitId = habit.getId();
        mockMvc.perform(myGetSimple(habitId)
                        .param("pageNumber", "0")
                        .param("pageSize", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.content.size()", Matchers.equalTo(5)));
    }
}
