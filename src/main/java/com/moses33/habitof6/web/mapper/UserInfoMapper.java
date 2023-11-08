package com.moses33.habitof6.web.mapper;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface UserInfoMapper {
    UserInfoDto userToUserInfoDto(User user);

    User updateUserFromUserInfoDto(UserInfoDto userInfoDto, @MappingTarget User user);
}
