package com.moses33.habitof6.web.auth;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@PreAuthorize("@habitAuthenticationManager.hasAccessToHabit(authentication, #habitId)")
public @interface HasAccessToHabit {
}
