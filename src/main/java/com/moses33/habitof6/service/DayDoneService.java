package com.moses33.habitof6.service;

import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface DayDoneService {
    Page<DayDoneDto> getDayDones(Integer habitId, PageRequest pageRequest);
}
