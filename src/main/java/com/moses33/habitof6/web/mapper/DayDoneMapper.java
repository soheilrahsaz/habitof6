package com.moses33.habitof6.web.mapper;


import com.moses33.habitof6.domain.DayDone;
import com.moses33.habitof6.web.dto.daydone.AddDayDoneDto;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface DayDoneMapper {
    DayDoneDto dayDoneToDayDoneDto(DayDone dayDone);
    DayDone addDayDoneToDayDone(AddDayDoneDto addDayDoneDto);
}
