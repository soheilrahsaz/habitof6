package com.moses33.habitof6.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
public class RegisterUserDto {
    @Length(max = 32)
    @NotEmpty
    private String username;

    @Length(max = 32)
    @NotEmpty
    private String password;

    @Length(max = 255)
    @NotEmpty
    @Email
    private String email;
}
