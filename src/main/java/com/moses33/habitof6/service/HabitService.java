package com.moses33.habitof6.service;

import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDto;
import com.moses33.habitof6.web.dto.habit.UpdateHabitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface HabitService {
    Page<HabitDto> getHabits(PageRequest pageRequest);

    HabitDto addHabit(CreateHabitDto habitDto);

    HabitDto updateHabit(UpdateHabitDto updateHabitDto, Integer habitId);
}
