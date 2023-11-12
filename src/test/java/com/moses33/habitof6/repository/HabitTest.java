package com.moses33.habitof6.repository;

import com.moses33.habitof6.domain.*;
import com.moses33.habitof6.repository.security.RoleRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.time.LocalDate;
import java.util.Random;

@SpringBootTest
class HabitTest {

    @Autowired
    HabitRepository habitRepository;

    @Autowired
    DayDoneRepository dayDoneRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Disabled("only for 1 time use")
    @Test
    @Commit
    void createManyHabitsAndDayDones() {

        final User savedUser;
        {
            User user = User.builder()
                    .username("userWithTestHabits")
                    .email("userWithTestHabits@lol.com")
                    .role(roleRepository.findByName("ROLE_USER").orElseThrow())
                    .password(new BCryptPasswordEncoder().encode("123456"))
                    .build();
            savedUser = userRepository.findWithRolesByUsername(user.getUsername())
                    .orElseGet(() -> userRepository.saveAndFlush(user));
        }

        int numberOfHabits = 100;
        int dayDonesPerHabit = 100;

        final LocalDate now = LocalDate.now();
        for (int i = 0; i < numberOfHabits; i++) {
            Habit habit = habitRepository.saveAndFlush(Habit.builder().name(RandomStringUtils.randomAlphabetic(6, 12))
                    .colorHex(generateRandomHexColor())
                    .user(savedUser)
                    .direction(Math.random() > 0.5 ? HabitDirection.ASC : HabitDirection.DESC)
                    .days("0,1,2,3,4,5,6")//TODO randomize and handle!
                    .build());
            LocalDate date = LocalDate.now().minusDays(dayDonesPerHabit);
            while (date.isBefore(now)) {
                date = date.plusDays(1);
                DayDone dayDone = DayDone.builder()
                        .date(date)
                        .habit(habit)
                        .build();
                double rnd = Math.random();
                if (rnd > 0.9) {//nothing
                    continue;
                } else if (rnd < 0.2) {//skip
                    dayDone.setType(DayDoneType.SKIP);
                } else {
                    dayDone.setType(DayDoneType.DONE);
                }
                dayDoneRepository.save(dayDone);
            }
            dayDoneRepository.flush();

            long dayDoneAdded = dayDoneRepository.countByHabit_Id(habit.getId());
            Assertions.assertThat(dayDoneAdded).isLessThanOrEqualTo(dayDonesPerHabit);
            Assertions.assertThat(dayDoneAdded).isGreaterThan(10);//let it be at least more than 10!! should be very unlucky if it's not
        }

        Assertions.assertThat(habitRepository.countByUser_Id(savedUser.getId())).isGreaterThanOrEqualTo(numberOfHabits);
    }

    public static String generateRandomHexColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Format the RGB values to hex and concatenate them with a '#' symbol
        String hexColor = String.format("#%02x%02x%02x", red, green, blue);
        return hexColor;
    }
}
