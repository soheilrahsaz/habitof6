package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.repository.HabitRepository;
import com.moses33.habitof6.web.dto.HabitDto;
import com.moses33.habitof6.web.mapper.HabitMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitServiceImpl extends BaseService implements HabitService {

    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;

    @Override
    public Page<HabitDto> getHabits(PageRequest pageRequest) {
        Page<Habit> habitPage = habitRepository.findAll(pageRequest);

        return new PageImpl<>(habitPage.getContent().stream().map(habitMapper::habitToHabitDto).toList(),
                habitPage.getPageable(),
                habitPage.getTotalElements());
    }

    @Override
    public HabitDto addHabit(HabitDto habitDto) {

        Habit habit = habitMapper.habitDtoToHabit(habitDto);
        habit.setId(null);
        habit.setUser(getLoggedInUser());

        return habitMapper.habitToHabitDto(habitRepository.save(habit));
    }
}
