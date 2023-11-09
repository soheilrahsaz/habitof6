package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.AuthService;
import com.moses33.habitof6.web.dto.auth.ChangePasswordDto;
import com.moses33.habitof6.web.dto.auth.LoginDto;
import com.moses33.habitof6.web.dto.auth.RegisterUserDto;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<Boolean> login(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
    {
        authService.login(loginDto, request, response);
        return new BaseResponse<>(true);
    }

    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody @Valid RegisterUserDto registerUserDto, HttpServletRequest request, HttpServletResponse response)
    {
        authService.register(registerUserDto, request, response);
        return new BaseResponse<>(true);
    }

    @GetMapping("/getUserInfo")
    public BaseResponse<UserInfoDto> getUserInfo()
    {
        return new BaseResponse<>(authService.getUserInfo());
    }

    @PostMapping("/updateUserInfo")
    public BaseResponse<UserInfoDto> updateUserInfo(@RequestBody @Valid UserInfoDto userInfoDto)
    {
        return new BaseResponse<>(authService.updateUserInfo(userInfoDto));
    }
    @PostMapping("/changePassword")
    public BaseResponse<Boolean> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
    {
        authService.changePassword(changePasswordDto);
        return new BaseResponse<>(true);
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response)
    {
        authService.logout(request, response);
        return new BaseResponse<>(true);
    }
}
