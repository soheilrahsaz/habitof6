package com.moses33.habitof6.web.auth;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HabitAuthenticationManager {
    private final HabitRepository habitRepository;

    public boolean hasAccessToHabit(Authentication authentication, Integer habitId){
        User user = (User)(authentication.getPrincipal());
        return habitRepository.existsByIdAndUser_Id(habitId, user.getId());
    }
}
