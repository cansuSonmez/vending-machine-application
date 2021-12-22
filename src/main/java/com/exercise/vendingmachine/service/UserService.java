package com.exercise.vendingmachine.service;

import com.exercise.vendingmachine.dto.DepositDto;
import com.exercise.vendingmachine.dto.UserDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;

public interface UserService {

    User createUser(UserDto userDto);

    User getUser(UserDetailsDto userDetailsDto, Long userId);

    User updateUser(UserDetailsDto userDetailsDto, Long userId, UserDto userDto);

    User deleteUser(UserDetailsDto userDetailsDto, Long userId);

}
