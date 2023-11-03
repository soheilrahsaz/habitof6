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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@Order(1)
public class SecurityLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(authorityRepository.count() == 8)
        {
            return;
        }

        Set<Authority> authorities = new HashSet<>();
        authorities.addAll(getCrudAuthoritiesForName("habit"));
        authorities.addAll(getCrudAuthoritiesForName("dayDone"));

        getOrCreateUserRole(authorities, "ROLE_USER");
    }

    private List<Authority> getCrudAuthoritiesForName(String name)
    {
        Authority read = getOrCreateAuthority(name+".read");
        Authority create = getOrCreateAuthority(name+".create");
        Authority update = getOrCreateAuthority(name+".update");
        Authority delete = getOrCreateAuthority(name+".delete");

        return List.of(read, create, update, delete);
    }
    private Authority getOrCreateAuthority(String name)
    {
        return authorityRepository.findByName(name).orElseGet(() -> authorityRepository.save(Authority.builder().name(name).build()));
    }
    private Role getOrCreateUserRole(Set<Authority> authorities, String name)
    {
        Role role = roleRepository.findByName(name).orElseGet(() -> Role.builder().name(name).build());
        role.setAuthorities(authorities);
        return roleRepository.save(role);
    }
}
