package com.moses33.habitof6.repository;


import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.repository.security.RoleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MysqlIntegratedTest {


    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Test
    void testPrimaryPersist() {
        PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .username("testUser")
                .password(bcryptEncoder.encode("testPassword"))
                .firstName("Soheil")
                .lastName("Rahsaz")
                .email("soheilrahsaz.sr@gmail.com")
                .build();
        User savedUser =  userRepository.saveAndFlush(user);

        assertThat(savedUser.getId()).isNotNull();

        //test version is updated
        assertThat(savedUser.getVersion()).isZero();
        savedUser.setFirstName("khiar");
        User savedUser2 = userRepository.saveAndFlush(savedUser);

        assertThat(savedUser2.getVersion()).isEqualTo(1);
    }

    @Test
    void testValidation() {
        final String veryLongString = RandomStringUtils.randomAlphabetic(1000);
        User user = User.builder()
                .firstName(veryLongString)
                .lastName(veryLongString)
                .email(veryLongString)
                .build();

        Set<ConstraintViolation<?>> constraintViolations = Assertions.assertThrows(ConstraintViolationException.class, () -> userRepository.save(user))
                .getConstraintViolations();
        assertThat(constraintViolations).isNotEmpty();
    }
}
