package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.HabitService;
import com.moses33.habitof6.web.dto.habit.CreateHabitDto;
import com.moses33.habitof6.web.dto.habit.HabitDto;
import com.moses33.habitof6.web.dto.habit.HabitFullDto;
import com.moses33.habitof6.web.dto.habit.UpdateHabitDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController extends BaseController{

    private final HabitService habitService;

    @GetMapping
    public BaseResponse<Page<HabitFullDto>> getHabits(@RequestParam(required = false) Integer pageNumber,
                                                      @RequestParam(required = false) Integer pageSize)
    {
        return new BaseResponse<>(habitService.getHabits(getPageRequest(pageNumber, pageSize)));
    }
    @PostMapping
    @PreAuthorize("hasAuthority('habit.create')")
    public BaseResponse<HabitDto> addHabit(@RequestBody @Valid CreateHabitDto createHabitDto)
    {
        return new BaseResponse<>(habitService.addHabit(createHabitDto), HttpStatus.CREATED);
    }

    @PutMapping("/{habitId}")
    @PreAuthorize("hasAuthority('habit.update')")
    public BaseResponse<HabitDto> updateHabit(@RequestBody @Valid UpdateHabitDto updateHabitDto, @PathVariable Integer habitId)
    {
        return new BaseResponse<>(habitService.updateHabit(updateHabitDto, habitId), HttpStatus.OK);
    }

    @DeleteMapping("/{habitId}")
    @PreAuthorize("hasAuthority('habit.delete')")
    public BaseResponse<Boolean> deleteHabit(@PathVariable Integer habitId)
    {
        return new BaseResponse<>(habitService.deleteHabit(habitId));
    }

}
