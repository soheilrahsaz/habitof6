package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmailAndIdNot(String email, Integer id);
    boolean existsByUsernameAndIdNot(String username, Integer id);

    @EntityGraph(attributePaths = {"roles", "roles.authorities"})
    Optional<User> findWithRolesByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteByUsername(String username);
}
