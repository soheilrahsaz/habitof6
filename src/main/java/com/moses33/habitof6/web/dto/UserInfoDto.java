package com.moses33.habitof6.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    @Length(max = 32)
    @NotEmpty
    private String username;

    @Length(max = 255)
    @NotEmpty
    @Email
    private String email;

    @Length(max = 64)
    @NotEmpty
    private String firstName;

    @Length(max = 64)
    @NotEmpty
    private String lastName;
}
