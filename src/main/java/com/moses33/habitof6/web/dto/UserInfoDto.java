package com.moses33.habitof6.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
