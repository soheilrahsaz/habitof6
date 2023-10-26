package com.moses33.habitof6.service;

import com.moses33.habitof6.domain.User;
import com.moses33.habitof6.web.exception.UserFriendlyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseService {

    public User getLoggedInUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
        {
            throw new UserFriendlyException("Not logged in");
        }

        if(!(authentication.getPrincipal() instanceof User))
        {
            throw new IllegalArgumentException("authentication is not an instanceof User");
        }
        return (User)authentication.getPrincipal();
    }
}
