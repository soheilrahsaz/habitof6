package com.moses33.habitof6.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends FullEntity{

    @Length(max = 32)
    @NotEmpty
    private String username;

    @Length(max = 60)
    @NotEmpty
    private String password;

    @Length(max = 64)
    private String firstName;

    @Length(max = 64)
    private String lastName;

    @Email
    @Length(max = 255)
    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Habit> habits;
}
