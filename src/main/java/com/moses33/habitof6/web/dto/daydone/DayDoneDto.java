package com.moses33.habitof6.web.dto.daydone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moses33.habitof6.domain.DayDoneType;
import com.moses33.habitof6.web.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DayDoneDto extends BaseDto {


    @JsonFormat(pattern="yyyy-MM-dd", shape=JsonFormat.Shape.STRING)
    private LocalDate date;

    private DayDoneType type;
}
