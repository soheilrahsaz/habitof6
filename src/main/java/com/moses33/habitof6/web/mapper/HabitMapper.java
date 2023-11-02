package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDirectionDto;
import com.moses33.habitof6.web.dto.habit.HabitDto;
import com.moses33.habitof6.web.dto.habit.UpdateHabitDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper(uses = DateMapper.class)
public abstract class HabitMapper {
    public abstract HabitDirectionDto habitDirectionToHabitDirectionDto(HabitDirection habitDirection);
    public abstract HabitDirection habitDirectionDtoToHabitDirection(HabitDirectionDto habitDirectionDto);

    public abstract HabitDto habitToHabitDto(Habit habit);

    public abstract Habit createHabitDtoToHabit(CreateHabitDto createHabitDto);
    public abstract Habit updateHabitDtoToHabit(UpdateHabitDto updateHabitDto);

    @AfterMapping
    public void createHabitAfterMapping(CreateHabitDto createHabitDto, @MappingTarget Habit.HabitBuilder<?, ?> habitBuilder)
    {
        orderDays(habitBuilder, createHabitDto.getDays());
    }
    @AfterMapping
    public void updateHabitAfterMapping(UpdateHabitDto updateHabitDto, @MappingTarget Habit.HabitBuilder<?, ?> habitBuilder)
    {
        orderDays(habitBuilder, updateHabitDto.getDays());
    }

    private void orderDays(Habit.HabitBuilder<?, ?> habitBuilder, String days)
    {
        habitBuilder.days(Arrays.stream(days.split(","))
                .sorted()
                .collect(Collectors.joining(",")));
    }

}
