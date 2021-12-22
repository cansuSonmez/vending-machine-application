package com.exercise.vendingmachine.serviceTest;

import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.dto.UserDto;
import com.exercise.vendingmachine.enumeration.UserRole;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    UserRepository userRepository;

    User user = new User(1L, "cansu", "123456", 0L, UserRole.SELLER);

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("cansu");
        userDto.setPassword(bCryptPasswordEncoder.encode("123456"));
        userDto.setRole(UserRole.BUYER);

        User result = User.builder()
                .id(null)
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .deposit(0L)
                .role(UserRole.BUYER)
                .build();
        when(userRepository.save(result)).thenReturn(user);
        assertEquals(userService.createUser(userDto).getUsername(), result.getUsername());
    }

    @Test
    public void testGetUser() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.getUser(userDetailsDto, 1L), user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = User.builder()
                .id(1L)
                .username("hasan")
                .password("123456")
                .deposit(5L)
                .role(UserRole.SELLER)
                .build();

        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        UserDto userDto = new UserDto();
        userDto.setUsername("cansu");
        userDto.setPassword("123456");
        userDto.setRole(UserRole.SELLER);

        User userUpdated = User.builder()
                .id(1L)
                .username("cansu")
                .password(bCryptPasswordEncoder.encode("123456"))
                .deposit(5L)
                .role(UserRole.SELLER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(userUpdated)).thenReturn(userUpdated);
        assertEquals(userService.updateUser(userDetailsDto,1L,userDto),userUpdated);
    }

    @Test
    public void testDeleteUser() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        assertEquals(userService.deleteUser(userDetailsDto, 1L), user);
    }

}

