package com.moses33.habitof6.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
@Constraint(validatedBy = HabitDaysValidator.class)
public @interface HabitDays {
    String message() default "Must be a csv of numbers between 0-6 and not duplicate numbers";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
