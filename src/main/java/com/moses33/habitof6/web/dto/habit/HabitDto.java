package com.moses33.habitof6.web.dto.habit;

import com.moses33.habitof6.web.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto extends BaseDto {

    private String name;

    private String colorHex;

    private HabitDirectionDto direction;

    private String days;

}
