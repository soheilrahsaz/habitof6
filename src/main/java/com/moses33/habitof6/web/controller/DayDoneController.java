package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.DayDoneService;
import com.moses33.habitof6.web.dto.daydone.AddDayDoneDto;
import com.moses33.habitof6.web.dto.daydone.DayDoneDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(BaseController.API_BASE_PATH+"/habit/{habitId}/dayDone")
@AllArgsConstructor
public class DayDoneController extends BaseController{
    private final DayDoneService dayDoneService;

    /*
     * TODO find a way to avoid code duplication for @habitAuthenticationManager.hasAccessToHabit(authentication, #habitId)
     *  and define custom annotation
     */

    @GetMapping
    @PreAuthorize("@habitAuthenticationManager.hasAccessToHabit(authentication, #habitId) && hasAuthority('dayDone.read')")
    public BaseResponse<Page<DayDoneDto>> getDayDones(@PathVariable @NotNull Integer habitId, @RequestParam(required = false) Integer pageNumber,
                                                      @RequestParam(required = false) Integer pageSize) {
        return new BaseResponse<>(dayDoneService.getDayDones(habitId, getPageRequest(pageNumber, pageSize)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("@habitAuthenticationManager.hasAccessToHabit(authentication, #habitId) && hasAuthority('dayDone.create') && hasAuthority('dayDone.update')")
    public BaseResponse<DayDoneDto> addDayDone(@PathVariable @NotNull Integer habitId, @RequestBody AddDayDoneDto addDayDoneDto)
    {
        return new BaseResponse<>(dayDoneService.addDayDone(habitId, addDayDoneDto), HttpStatus.OK);
    }

    @DeleteMapping("/{date}")
    @PreAuthorize("@habitAuthenticationManager.hasAccessToHabit(authentication, #habitId) && hasAuthority('dayDone.create')")
    public BaseResponse<Boolean> deleteDayDone(@PathVariable @NotNull Integer habitId,
                                               @PathVariable @NotNull LocalDate date)
    {
        return new BaseResponse<>(dayDoneService.deleteDayDone(habitId, date), HttpStatus.OK);
    }
}
