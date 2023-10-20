package com.moses33.habitof6.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserDto {
    private String username;
    private String password;
    private String email;
}
