package com.moses33.habitof6.security;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

class EncryptionTest {

    @Test
    void bcryptTest() {
        String password = "123456";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Assertions.assertThat(passwordEncoder.encode(password)).hasSize(60);
    }
}
