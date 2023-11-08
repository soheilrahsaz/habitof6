package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.domain.security.Role;
import com.moses33.habitof6.repository.UserRepository;
import com.moses33.habitof6.repository.security.LoginFailureRepository;
import com.moses33.habitof6.repository.security.RoleRepository;
import com.moses33.habitof6.web.dto.auth.LoginDto;
import com.moses33.habitof6.web.dto.auth.RegisterUserDto;
import com.moses33.habitof6.web.dto.auth.UserInfoDto;
import com.moses33.habitof6.web.exception.UserFriendlyException;
import com.moses33.habitof6.web.mapper.UserInfoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends BaseService implements AuthService {

    private final LoginFailureRepository loginFailureRepository;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper userInfoMapper;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
    private final WebAuthenticationDetailsSource webAuthenticationDetailsSource = new WebAuthenticationDetailsSource();

    @Override
    public void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        WebAuthenticationDetails details = this.webAuthenticationDetailsSource.buildDetails(request);
        if(loginFailureRepository.existsBySourceIpAndBlockDatetimeGreaterThanEqual(details.getRemoteAddress(), Timestamp.from(Instant.now())))
        {
            throw new InsufficientAuthenticationException("Too Many Attempts");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        token.setDetails(details);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolderStrategy contextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext emptyContext = contextHolderStrategy.createEmptyContext();
        emptyContext.setAuthentication(authentication);
        contextHolderStrategy.setContext(emptyContext);
        securityContextRepository.saveContext(emptyContext, request, response);
    }

    @Override
    public void register(RegisterUserDto registerUserDto, HttpServletRequest request, HttpServletResponse response) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new UserFriendlyException("Username already exists");
        }

        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new UserFriendlyException("Email already exists");
        }

        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();
        User user = User.builder()
                .username(registerUserDto.getUsername())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .email(registerUserDto.getEmail())
                .role(userRole)
                .build();

        userRepository.save(user);
        login(new LoginDto(registerUserDto.getUsername(), registerUserDto.getPassword()), request, response);

    }

    @Override
    public UserInfoDto getUserInfo() {
        return userInfoMapper.userToUserInfoDto(getLoggedInUser());
    }

    @Override
    public UserInfoDto updateUserInfo(UserInfoDto userInfoDto) {
        User user = userRepository.findById(getLoggedInUser().getId()).orElseThrow();
        if (userRepository.existsByUsernameAndIdNot(userInfoDto.getUsername(), user.getId())) {
            throw new UserFriendlyException("Username already exists");
        }

        if (userRepository.existsByEmailAndIdNot(userInfoDto.getEmail(), user.getId())) {
            throw new UserFriendlyException("Email already exists");
        }

        user =  userInfoMapper.updateUserFromUserInfoDto(userInfoDto, user);
        return this.userInfoMapper.userToUserInfoDto(userRepository.save(user));
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
        {
            throw new UserFriendlyException("Not logged in");
        }

        this.securityContextLogoutHandler.logout(request, response, authentication);
    }
}
