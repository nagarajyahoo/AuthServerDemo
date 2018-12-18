package com.example.authserver.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        } else {
            return getUserDetails();
        }
    }

    private UserDetails getUserDetails() {
        return User.withUsername("nmr")
                .password("{noop}nmr@123")
                .roles("USER")
                .build();
    }
}