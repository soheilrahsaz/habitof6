package com.moses33.habitof6.web.dto.auth;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Length(max = 32)
    @NotEmpty
    private String username;

    @Length(max = 32)
    @NotEmpty
    private String password;

}
