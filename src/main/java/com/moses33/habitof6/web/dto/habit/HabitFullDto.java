package com.moses33.habitof6.web.dto.habit;

import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HabitFullDto extends HabitDto {

    private List<DayDoneDto> dayDones;
}
