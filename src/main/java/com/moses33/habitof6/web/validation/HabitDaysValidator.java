package com.moses33.habitof6.web.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.stream.Stream;

public class HabitDaysValidator implements ConstraintValidator<HabitDays, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null || value.isBlank())
            return false;

        String[] parts = value.split(",");
        return Stream.of(parts).allMatch(str -> str.matches("^[0-6]$"))//all numbers between 0-6
                && Stream.of(parts).allMatch(new HashSet<>()::add);//all unique
    }
}
