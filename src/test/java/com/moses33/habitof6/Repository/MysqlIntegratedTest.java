package com.moses33.habitof6.Repository;


import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.DigestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MysqlIntegratedTest {


    @Autowired
    UserRepository userRepository;

    @Test
    void testPrimaryPersist() {
        User user = User.builder()
                .username("testUser")
                .password(DigestUtils.md5DigestAsHex("testPassword".getBytes()))
                .firstName("Soheil")
                .lastName("Rahsaz")
                .email("soheilrahsaz.sr@gmail.com")
                .build();
        User savedUser =  userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
    }
}
