package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.domain.security.Role;
import com.moses33.habitof6.repository.UserRepository;
import com.moses33.habitof6.repository.security.RoleRepository;
import com.moses33.habitof6.web.dto.LoginDto;
import com.moses33.habitof6.web.dto.RegisterUserDto;
import com.moses33.habitof6.web.dto.UserInfoDto;
import com.moses33.habitof6.web.exception.UserFriendlyException;
import com.moses33.habitof6.web.mapper.UserInfoMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper userInfoMapper;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();

    @Override
    public void login(LoginDto loginDto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
        {
            throw new UserFriendlyException("Not logged in");
        }

        if(!(authentication.getPrincipal() instanceof User))
        {
            throw new IllegalArgumentException("authentication is not an instanceof User");
        }

        return userInfoMapper.userToUserInfoDto((User)authentication.getPrincipal());
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