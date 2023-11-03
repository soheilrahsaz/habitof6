package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.DayDone;
import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.repository.DayDoneRepository;
import com.moses33.habitof6.web.dto.daydone.AddDayDoneDto;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import com.moses33.habitof6.web.mapper.DayDoneMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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

    @Override
    @Transactional
    public DayDoneDto addDayDone(Integer habitId, AddDayDoneDto addDayDoneDto) {
        dayDoneRepository.deleteByHabit_IdAndDate(habitId, addDayDoneDto.getDate());
        dayDoneRepository.flush();//flushing to make sure that this actually deletes before trying to insert new

        DayDone dayDone = dayDoneMapper.addDayDoneToDayDone(addDayDoneDto);
        dayDone.setHabit(Habit.builder().id(habitId).version(1L).build());

        //TODO actually we don't need a delete before insert, we need a replace query.
        return dayDoneMapper.dayDoneToDayDoneDto(dayDoneRepository.save(dayDone));
    }

    @Override
    @Transactional
    public Boolean deleteDayDone(Integer habitId, LocalDate date) {
        return dayDoneRepository.deleteByHabit_IdAndDate(habitId, date) > 0;
    }
}
