package com.moses33.habitof6.service;

import com.moses33.habitof6.web.dto.HabitDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface HabitService {
    Page<HabitDto> getHabits(PageRequest pageRequest);

    HabitDto addHabit(HabitDto habitDto);
}
