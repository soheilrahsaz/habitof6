package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.dto.HabitDirectionDto;
import com.moses33.habitof6.web.dto.HabitDto;
import com.moses33.habitof6.web.dto.UserInfoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest({UserInfoMapper.class, HabitMapper.class})
class MapperTest {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    HabitMapper habitMapper;

    @Test
    void testMapUserInfo() {
        User user = User.builder()
                .username("soheil33")
                .firstName("soheil")
                .lastName("rahsaz")
                .email("soheilrahsaz.sr@gmail.com")
                .build();

        UserInfoDto userInfoDto = userInfoMapper.userToUserInfoDto(user);

        assertEquals(user.getUsername(), userInfoDto.getUsername());
        assertEquals(user.getFirstName(), userInfoDto.getFirstName());
        assertEquals(user.getEmail(), userInfoDto.getEmail());
        assertEquals(user.getLastName(), userInfoDto.getLastName());
    }

    @Test
    void testMapHabit() {
        HabitDirection habitDirection = HabitDirection.ASC;
        HabitDirectionDto habitDirectionDto = habitMapper.habitDirectionToHabitDirectionDto(habitDirection);

        assertEquals(habitDirection.toString(), habitDirectionDto.toString());

        Habit habit = Habit.builder()
                .name("Eat")
                .colorHex("#4287f5")
                .days("0,1,2,4,5")
                .direction(HabitDirection.ASC)
                .build();

        HabitDto habitDto = habitMapper.habitToHabitDto(habit);
        assertEquals(habitDto.getName(), habit.getName());
        assertEquals(habitDto.getColorHex(), habit.getColorHex());
        assertEquals(habitDto.getDays(), habit.getDays());
        assertEquals(habitDto.getDirection().toString(), habit.getDirection().toString());

    }
}
