package com.moses33.habitof6.web.dto;

import com.moses33.habitof6.web.validation.HabitDays;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HabitDto extends BaseDto{

    @Length(max = 128)
    @NotEmpty
    private String name;

    @JdbcTypeCode(SqlTypes.CHAR)
    @NotEmpty
    @Pattern(regexp = "#\\d{6}")
    private String colorHex;

    @NotNull
    private HabitDirectionDto direction;

    @HabitDays
    private String days;

}
