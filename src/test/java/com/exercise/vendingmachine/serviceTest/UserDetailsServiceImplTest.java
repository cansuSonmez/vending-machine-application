package com.exercise.vendingmachine.serviceTest;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.enumeration.UserRole;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.Table;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    UserRepository userRepository;

    @Mock
    UserDetailsService userDetailsService ;

    User user = new User(1L, "cansu", "123456", 0L, UserRole.SELLER);

    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);
        when(userRepository.findTopByUsername(user.getUsername())).thenReturn(Optional.of(user));
        assertEquals(userDetailsServiceImpl.loadUserByUsername(user.getUsername()).getUsername(),userDetailsDto.getUsername());
    }
}
