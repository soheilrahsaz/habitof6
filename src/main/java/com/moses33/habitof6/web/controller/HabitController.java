package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.HabitService;
import com.moses33.habitof6.web.dto.HabitDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit")
@RequiredArgsConstructor
public class HabitController {

    private final HabitService habitService;

    @GetMapping
    public BaseResponse<Page<HabitDto>> getHabits(@RequestParam(required = false) Integer pageNumber,
                                                  @RequestParam(required = false) Integer pageSize)
    {
        return new BaseResponse<>(habitService.getHabits(getPageRequest(pageNumber, pageSize)));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('habit.create')")
    public BaseResponse<HabitDto> addHabit(@RequestBody @Valid HabitDto habitDto)
    {
        return new BaseResponse<>(habitService.addHabit(habitDto), HttpStatus.CREATED);
    }

    private PageRequest getPageRequest(Integer pageNumber, Integer pageSize)
    {
        return PageRequest.of(pageNumber == null ? 0 : 1, Math.max(pageSize == null ? 0 : pageSize, 20));
    }
}