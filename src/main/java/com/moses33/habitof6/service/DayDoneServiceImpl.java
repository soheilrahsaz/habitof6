package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.DayDone;
import com.moses33.habitof6.repository.DayDoneRepository;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import com.moses33.habitof6.web.mapper.DayDoneMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DayDoneServiceImpl extends BaseService implements DayDoneService {

    private final DayDoneRepository dayDoneRepository;
    private final DayDoneMapper dayDoneMapper;

    @Override
    public Page<DayDoneDto> getDayDones(Integer habitId, PageRequest pageRequest) {
        Page<DayDone> dayDonesPage = dayDoneRepository.findByHabit_Id(habitId, pageRequest);

        return new PageImpl<>(dayDonesPage.getContent().stream().map(dayDoneMapper::dayDoneToDayDoneDto).toList(),
                dayDonesPage.getPageable(), dayDonesPage.getTotalElements());
    }
}
