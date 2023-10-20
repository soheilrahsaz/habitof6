package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.dto.UserInfoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(UserInfoMapper.class)
class MapperTest {

    @Autowired
    UserInfoMapper userInfoMapper;

    @Test
    void testMapUserInfo() {
        User user = User.builder()
                .username("soheil33")
                .firstName("soheil")
                .lastName("rahsaz")
                .email("soheilrahsaz.sr@gmail.com")
                .build();

        UserInfoDto userInfoDto = userInfoMapper.userToUserInfoDto(user);

        Assertions.assertEquals(user.getUsername(), userInfoDto.getUsername());
        Assertions.assertEquals(user.getFirstName(), userInfoDto.getFirstName());
        Assertions.assertEquals(user.getEmail(), userInfoDto.getEmail());
        Assertions.assertEquals(user.getLastName(), userInfoDto.getLastName());
    }
}
