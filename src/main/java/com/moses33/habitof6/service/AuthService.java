package com.moses33.habitof6.service;

import com.moses33.habitof6.web.dto.LoginDto;
import com.moses33.habitof6.web.dto.RegisterUserDto;
import com.moses33.habitof6.web.dto.UserInfoDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response);
    void register(RegisterUserDto registerUserDto, HttpServletRequest request, HttpServletResponse response);
    UserInfoDto getUserInfo();
    void logout(HttpServletRequest request, HttpServletResponse response);
}
