package com.exercise.vendingmachine.dto;

import com.exercise.vendingmachine.enumeration.UserRole;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {

    @NotEmpty
    @Size(min = 2, max = 64)
    public String username;

    @NotEmpty
    @Size(min = 6, max = 32)
    public String password;

    // public Long deposit;

    @NotNull
    public UserRole role;

}
