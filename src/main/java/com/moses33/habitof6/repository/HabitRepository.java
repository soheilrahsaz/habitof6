package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitRepository extends JpaRepository<Habit, Integer> {
}