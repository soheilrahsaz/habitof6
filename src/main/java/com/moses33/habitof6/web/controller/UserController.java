package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.AuthService;
import com.moses33.habitof6.web.dto.auth.ChangePasswordDto;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthService authService;

    @GetMapping()
    public BaseResponse<UserInfoDto> getUserInfo()
    {
        return new BaseResponse<>(authService.getUserInfo());
    }

    @PatchMapping()
    public BaseResponse<UserInfoDto> updateUserInfo(@RequestBody @Valid UserInfoDto userInfoDto)
    {
        return new BaseResponse<>(authService.updateUserInfo(userInfoDto));
    }

    @PatchMapping("/password")
    public BaseResponse<Boolean> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
    {
        authService.changePassword(changePasswordDto);
        return new BaseResponse<>(true);
    }


}
