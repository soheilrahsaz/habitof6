package com.moses33.habitof6.web.dto.daydone;

import com.moses33.habitof6.domain.DayDoneType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddDayDoneDto {

    @NotNull
    private LocalDate date;

    @NotNull
    private DayDoneType type;
}
