package com.moses33.habitof6.web.dto.auth;

import com.moses33.habitof6.web.validation.PasswordsMatch;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordsMatch
public class ChangePasswordDto {

    @Length(max = 32)
    @NotEmpty
    private String password;
    @Length(max = 32)
    @NotEmpty
    private String reTypePassword;
}
