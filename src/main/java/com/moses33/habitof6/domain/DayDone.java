package com.moses33.habitof6.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DayDone extends FullEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Habit habit;

    @JdbcTypeCode(SqlTypes.DATE)
    @NotNull
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @NotNull
    private DayDoneType type;
}
