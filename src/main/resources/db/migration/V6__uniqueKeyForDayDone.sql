ALTER TABLE day_done
    ADD UNIQUE `date_habit` (`date`, `habit_id`);