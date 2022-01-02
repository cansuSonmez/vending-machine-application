package com.exercise.vendingmachine.service.impl;

import com.exercise.vendingmachine.config.FilterConfig;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
// @Qualifier("VendingMachineUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findTopByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));
        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("Load User By Username ", user);

        return new UserDetailsDto(user);
    }

}
