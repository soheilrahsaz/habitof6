package com.moses33.habitof6.web.validation;

import com.moses33.habitof6.web.dto.auth.ChangePasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, ChangePasswordDto> {
    @Override
    public void initialize(PasswordsMatch constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ChangePasswordDto value, ConstraintValidatorContext context) {
        return Objects.equals(value.getPassword(), value.getReTypePassword());
    }
}
