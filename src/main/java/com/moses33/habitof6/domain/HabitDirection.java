package com.moses33.habitof6.domain;

import lombok.Getter;

@Getter
public enum HabitDirection {
    ASC("Ascending"), DESC("Descending");

    HabitDirection(String name) {
        this.name = name;
    }

    private final String name;
}
