package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
