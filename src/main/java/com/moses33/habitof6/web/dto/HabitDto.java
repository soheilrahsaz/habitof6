package com.moses33.habitof6.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    @Length(min = 7, max = 7)
    @NotEmpty
    private String colorHex;

    @NotNull
    private HabitDirectionDto direction;

    private String days;

}
