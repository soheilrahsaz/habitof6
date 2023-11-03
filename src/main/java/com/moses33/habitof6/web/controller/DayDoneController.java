package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.DayDoneService;
import com.moses33.habitof6.web.auth.HasAccessToHabit;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/habit/{habitId}/dayDone")
@AllArgsConstructor
public class DayDoneController extends BaseController{
    private final DayDoneService dayDoneService;

    @GetMapping
    @HasAccessToHabit
    public BaseResponse<Page<DayDoneDto>> getDayDones(@PathVariable @NotNull Integer habitId, @RequestParam(required = false) Integer pageNumber,
                                                      @RequestParam(required = false) Integer pageSize) {
        return new BaseResponse<>(dayDoneService.getDayDones(habitId, getPageRequest(pageNumber, pageSize)), HttpStatus.OK);
    }
}
