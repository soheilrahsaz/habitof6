package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.web.dto.HabitDirectionDto;
import com.moses33.habitof6.web.dto.HabitDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper
public abstract class HabitMapper {
    public abstract HabitDirectionDto habitDirectionToHabitDirectionDto(HabitDirection habitDirection);
    public abstract HabitDirection habitDirectionDtoToHabitDirection(HabitDirectionDto habitDirectionDto);

    public abstract HabitDto habitToHabitDto(Habit habit);
    public abstract Habit habitDtoToHabit(HabitDto dto);

    @AfterMapping
    public void orderDays(HabitDto habitDto, @MappingTarget Habit.HabitBuilder habitBuilder)
    {
        habitBuilder.days(Arrays.stream(habitDto.getDays().split(","))
                .sorted()
                .collect(Collectors.joining(",")));
    }

}
