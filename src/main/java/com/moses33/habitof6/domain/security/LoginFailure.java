package com.moses33.habitof6.domain.security;

import com.moses33.habitof6.domain.FullEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LoginFailure extends FullEntity {
    private String sourceIp;

    @Builder.Default
    private Integer wrongAttempts = 0;

    private Timestamp blockDatetime;
}
