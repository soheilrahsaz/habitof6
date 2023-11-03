package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.DayDone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayDoneRepository extends JpaRepository<DayDone, Integer> {
    Page<DayDone> findByHabit_Id(Integer id, Pageable pageable);
}