package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.dto.UserInfoDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserInfoMapper {
    UserInfoDto userToUserInfoDto(User user);
}
