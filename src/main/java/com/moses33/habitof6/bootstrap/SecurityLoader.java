package com.moses33.habitof6.bootstrap;

import com.moses33.habitof6.domain.security.Authority;
import com.moses33.habitof6.domain.security.Role;
import com.moses33.habitof6.repository.security.AuthorityRepository;
import com.moses33.habitof6.repository.security.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Order(1)
public class SecurityLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(authorityRepository.count() > 0)
        {//already loaded data, we are optimistic that if it's > 0, then everything is actually there
            return;
        }

        Authority readHabit = authorityRepository.save(Authority.builder().name("habit.read").build());
        Authority createHabit = authorityRepository.save(Authority.builder().name("habit.create").build());
        Authority updateHabit = authorityRepository.save(Authority.builder().name("habit.update").build());
        Authority deleteHabit = authorityRepository.save(Authority.builder().name("habit.delete").build());

        roleRepository.save(Role.builder().name("ROLE_USER")
                .authority(readHabit)
                .authority(createHabit)
                .authority(updateHabit)
                .authority(deleteHabit)
                .build());
    }
}
