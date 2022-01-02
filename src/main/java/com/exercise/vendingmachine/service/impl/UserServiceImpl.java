package com.exercise.vendingmachine.service.impl;

import com.exercise.vendingmachine.config.FilterConfig;
import com.exercise.vendingmachine.dto.UserDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.advice.exception.EntityNotFoundException;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepository userRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
    }

    /*
     * Create user can be called by anyone
     */
    @Override
    @Transactional
    public User createUser(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .deposit(0L)
                .role(userDto.getRole())
                .build();

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User created", user);

        return this.userRepository.save(user);
    }

    @Override
    public User getUser(UserDetailsDto userDetailsDto, Long userId) {
        checkUserPermission(userDetailsDto, userId);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User found", userId);

        return this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    @Transactional
    public User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto) {
        checkUserPermission(userDetailsDto, userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        // user.setDeposit(userDto.getDeposit());
        user.setRole(userDto.getRole());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User updated", user);
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public User deleteUser(UserDetailsDto userDetailsDto, Long userId) {
        checkUserPermission(userDetailsDto, userId);
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        this.userRepository.delete(user);

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.debug("User deleted", user);

        return user;
    }

    /*
     * Strict security policy applied, users can only get or modify their own accounts
     */
    private static void checkUserPermission(UserDetailsDto userDetailsDto, Long userId) {
        if (!userDetailsDto.getUser().getId().equals(userId)) {

            MDC.put("ip", FilterConfig.IP_ADDRESS);
            MDC.put("url", FilterConfig.URL_ADDRESS );
            MDC.put("session",FilterConfig.SESSION_ID);
            MDC.put("agent",FilterConfig.USER_AGENT);
            log.debug("User access denies", userDetailsDto);

            throw new AccessDeniedException("Access denied");
        }
    }

}
