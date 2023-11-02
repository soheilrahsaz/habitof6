package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.Habit;
import com.moses33.habitof6.repository.HabitRepository;
import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDto;
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
        Page<Habit> habitPage = habitRepository.findHabitsSecure(pageRequest);

        return new PageImpl<>(habitPage.getContent().stream().map(habitMapper::habitToHabitDto).toList(),
                habitPage.getPageable(),
                habitPage.getTotalElements());
    }

    @Override
    public HabitDto addHabit(CreateHabitDto createHabitDto) {

        Habit habit = habitMapper.createHabitDtoToHabit(createHabitDto);
        habit.setUser(getLoggedInUser());

        return habitMapper.habitToHabitDto(habitRepository.save(habit));
    }
}
