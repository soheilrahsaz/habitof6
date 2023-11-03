package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.Habit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
    boolean existsByIdAndUser_Id(Integer id, Integer userId);
    long deleteByIdAndUser_Id(Integer id, Integer userId);

    @Query("select h from Habit h where h.user.id = ?#{principal.id}")
    Page<Habit> findHabitsSecure(Pageable pageable);

    Optional<Habit> findByIdAndUser_Id(Integer id, Integer userId);

    Optional<Habit> findByName(String name);

}