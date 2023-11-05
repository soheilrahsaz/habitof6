package com.moses33.habitof6.security.listener;

import com.moses33.habitof6.domain.security.LoginFailure;
import com.moses33.habitof6.repository.security.LoginFailureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuthenticationListener {
    private final LoginFailureRepository loginFailureRepository;
    public static final Integer MAX_WRONG_ATTEMPTS = 0;

    @EventListener
    @Transactional
    public void listenFailed(AuthenticationFailureBadCredentialsEvent event) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) event.getSource();
        WebAuthenticationDetails details = ((WebAuthenticationDetails) token.getDetails());
        final String sourceIp = details.getRemoteAddress();

        LoginFailure loginFailure = loginFailureRepository.findBySourceIp(sourceIp).orElseGet(() -> LoginFailure.builder()
                .sourceIp(sourceIp)
                .build());
        loginFailure.setWrongAttempts(loginFailure.getWrongAttempts() + 1);
        if(loginFailure.getWrongAttempts() >= MAX_WRONG_ATTEMPTS)
        {
            loginFailure.setBlockDatetime(Timestamp.from(Instant.now()));
        }
        this.loginFailureRepository.save(loginFailure);
    }

}
