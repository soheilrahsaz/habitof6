package com.moses33.habitof6.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Entity
public class Habit extends BaseEntity{

    @Length(max = 128)
    @NotEmpty
    private String name;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Length(min = 7, max = 7)
    @NotEmpty
    private String colorHex;

    @Enumerated(EnumType.STRING)
    @NotNull
    private HabitDirection direction;

    @Length(max = 13)
    private String days;

    @ManyToOne
    private User user;
}

@Getter
enum HabitDirection{
    ASC("Ascending"),DESC("Descending");

    HabitDirection(String name) {
        this.name = name;
    }

    private final String name;
}
