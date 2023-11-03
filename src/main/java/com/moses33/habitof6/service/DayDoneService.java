package com.moses33.habitof6.service;

import com.moses33.habitof6.web.dto.daydone.AddDayDoneDto;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

public interface DayDoneService {
    Page<DayDoneDto> getDayDones(Integer habitId, PageRequest pageRequest);

    DayDoneDto addDayDone(Integer habitId, AddDayDoneDto addDayDoneDto);

    Boolean deleteDayDone(Integer habitId, LocalDate date);
}
