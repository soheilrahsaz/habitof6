package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.web.dto.HabitDirectionDto;
import com.moses33.habitof6.web.dto.HabitDto;
import org.mapstruct.Mapper;

@Mapper
public interface HabitMapper {
    HabitDirectionDto habitDirectionToHabitDirectionDto(HabitDirection habitDirection);
    HabitDirection habitDirectionDtoToHabitDirection(HabitDirectionDto habitDirectionDto);

    HabitDto habitToHabitDto(Habit habit);
    Habit habitDtoToHabit(HabitDto dto);
}
