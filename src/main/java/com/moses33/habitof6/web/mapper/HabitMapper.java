package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.domain.HabitDirection;
import com.moses33.habitof6.web.dto.habit.*;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Arrays;
import java.util.stream.Collectors;

@Mapper(uses = {DateMapper.class, DayDoneMapper.class})
public abstract class HabitMapper {
    public abstract HabitDirectionDto habitDirectionToHabitDirectionDto(HabitDirection habitDirection);
    public abstract HabitDirection habitDirectionDtoToHabitDirection(HabitDirectionDto habitDirectionDto);

    public abstract HabitDto habitToHabitDto(Habit habit);
    public abstract HabitFullDto habitFullDto(Habit habit);

    public abstract Habit createHabitDtoToHabit(CreateHabitDto createHabitDto);

    public abstract UpdateHabitDto habitToUpdateHabitDto(Habit habit);
    public abstract Habit updateHabitFromUpdateHabitDto(UpdateHabitDto updateHabitDto, @MappingTarget Habit habit);

    @AfterMapping
    public void createHabitAfterMapping(CreateHabitDto createHabitDto, @MappingTarget Habit.HabitBuilder<?, ?> habitBuilder)
    {
        habitBuilder.days(getOrderedDays(createHabitDto.getDays()));
    }
    @AfterMapping
    public void updateHabitAfterMapping(UpdateHabitDto updateHabitDto, @MappingTarget Habit habit)
    {
        habit.setDays(getOrderedDays(updateHabitDto.getDays()));
    }

    private String getOrderedDays(String days)
    {
        return Arrays.stream(days.split(","))
                .sorted()
                .collect(Collectors.joining(","));
    }
}
