package com.exercise.vendingmachine.controller;

import com.exercise.vendingmachine.dto.UserDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping(
        value = "/users",
        produces=MediaType.APPLICATION_JSON_VALUE,
        consumes=MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
     * Create user can be called by anyone
     */
    @PostMapping
    private @ResponseBody
    User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    private @ResponseBody
    User getUser(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @PathVariable Long userId) {
        return userService.getUser(userDetailsDto, userId);
    }

    @PutMapping("/{userId}")
    private @ResponseBody
    User updateUser(
            @AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @PathVariable Long userId,
            @RequestBody @Valid UserDto userDto) {
        return userService.updateUser(userDetailsDto, userId, userDto);
    }

    @DeleteMapping("/{userId}")
    private @ResponseBody
    User deleteUser(@AuthenticationPrincipal UserDetailsDto userDetailsDto, @PathVariable Long userId) {
        return userService.deleteUser(userDetailsDto, userId);
    }

}
