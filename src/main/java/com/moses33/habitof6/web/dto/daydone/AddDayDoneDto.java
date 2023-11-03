package com.moses33.habitof6.web.dto.daydone;

import com.moses33.habitof6.domain.DayDoneType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddDayDoneDto {

    @NotNull
    private Date date;

    @NotNull
    private DayDoneType type;
}
