package com.moses33.habitof6.repository.security;

import com.moses33.habitof6.domain.security.LoginFailure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {
    void deleteBySourceIp(String sourceIp);
    Optional<LoginFailure> findBySourceIp(String sourceIp);
}