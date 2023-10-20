package com.moses33.habitof6.web.controller;

import com.moses33.habitof6.service.AuthService;
import com.moses33.habitof6.web.dto.LoginDto;
import com.moses33.habitof6.web.dto.RegisterUserDto;
import com.moses33.habitof6.web.dto.UserInfoDto;
import com.moses33.habitof6.web.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public BaseResponse<Boolean> login(@RequestBody LoginDto loginDto, HttpServletRequest request, HttpServletResponse response)
    {
        authService.login(loginDto, request, response);
        return new BaseResponse<>(true);
    }

    @PostMapping("/register")
    public BaseResponse<Boolean> register(@RequestBody RegisterUserDto registerUserDto, HttpServletRequest request, HttpServletResponse response)
    {
        authService.register(registerUserDto, request, response);
        return new BaseResponse<>(true);
    }

    @GetMapping("/getUserInfo")
    public BaseResponse<UserInfoDto> getUserInfo()
    {
        return new BaseResponse<>(authService.getUserInfo());
    }

    @GetMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request, HttpServletResponse response)
    {
        authService.logout(request, response);
        return new BaseResponse<>(true);
    }
}
