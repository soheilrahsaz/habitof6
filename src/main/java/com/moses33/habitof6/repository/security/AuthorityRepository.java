package com.moses33.habitof6.repository.security;

import com.moses33.habitof6.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}