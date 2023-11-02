package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDirectionDto;
import com.moses33.habitof6.web.dto.habit.HabitDto;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import com.moses33.habitof6.web.dto.habit.UpdateHabitDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest({UserInfoMapper.class, HabitMapper.class, DateMapper.class})
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
                .id(1)
                .version(1L)
                .createdDate(Timestamp.valueOf(LocalDateTime.now().minusDays(1)))
                .name("Eat")
                .colorHex("#4287f5")
                .days("0,1,2,4,5")
                .direction(HabitDirection.ASC)
                .build();

        HabitDto habitDto = habitMapper.habitToHabitDto(habit);
        assertEquals(habitDto.getId(), habit.getId());
        assertEquals(habitDto.getCreatedDate().toLocalDateTime(), habit.getCreatedDate().toLocalDateTime());
        assertEquals(habitDto.getVersion(), habit.getVersion());
        assertEquals(habitDto.getName(), habit.getName());
        assertEquals(habitDto.getColorHex(), habit.getColorHex());
        assertEquals(habitDto.getDays(), habit.getDays());
        assertEquals(habitDto.getDirection().toString(), habit.getDirection().toString());


        CreateHabitDto createHabitDto = CreateHabitDto.builder()
                .days("5,4,3,2,1")
                .build();

        Habit fromCreate = habitMapper.createHabitDtoToHabit(createHabitDto);
        assertEquals("1,2,3,4,5", fromCreate.getDays());

        UpdateHabitDto updateHabitDto = UpdateHabitDto.builder()
                .days("5,4,2,3,1")
                .build();

        Habit fromUpdate = habitMapper.updateHabitDtoToHabit(updateHabitDto);
        assertEquals("1,2,3,4,5", fromUpdate.getDays());


    }
}
