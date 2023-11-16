package com.moses33.habitof6.security;

import com.moses33.habitof6.web.controller.BaseController;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    //TODO use jwt tokens and remove http basic and session tracking
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(antMatcher("/v3/api-docs/**"), antMatcher("/swagger-ui/**"), antMatcher("/swagger-ui.html")).permitAll()//openApi
                        .requestMatchers(antMatcher(BaseController.API_BASE_PATH+"/auth/register"), antMatcher(BaseController.API_BASE_PATH+"/auth/login"), antMatcher(BaseController.API_BASE_PATH+"/auth/logout")).permitAll()
                        .requestMatchers(antMatcher(BaseController.API_BASE_PATH+"/**")).authenticated()
                        .anyRequest().permitAll()
                ).httpBasic(configurer ->
                        configurer.addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
                            @Override
                            public <O extends BasicAuthenticationFilter> O postProcess(O filter) {
                                filter.setSecurityContextRepository(securityContextRepository());
                                return filter;
                            }
                        })
                )
                .sessionManagement(configurer ->
                        configurer
                                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                );
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationEventPublisher authenticationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new DefaultAuthenticationEventPublisher(applicationEventPublisher);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();//I know the default is bcrypt...
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

}


