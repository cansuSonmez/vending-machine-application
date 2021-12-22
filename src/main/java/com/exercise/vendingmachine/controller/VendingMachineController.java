package com.exercise.vendingmachine.controller;

import com.exercise.vendingmachine.dto.BuyResponseDto;
import com.exercise.vendingmachine.dto.DepositDto;
import com.exercise.vendingmachine.dto.PurchaseDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.service.VendingMachineService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        produces= MediaType.APPLICATION_JSON_VALUE,
        consumes=MediaType.APPLICATION_JSON_VALUE)
public class VendingMachineController {

    private final VendingMachineService vendingMachineService;

    public VendingMachineController(VendingMachineService vendingMachineService) {
        this.vendingMachineService = vendingMachineService;
    }

    @PostMapping("/deposit")
    private @ResponseBody
    User deposit(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @RequestBody @Valid DepositDto depositDto) {
        return vendingMachineService.deposit(userDetailsDto, depositDto);
    }

    @PostMapping("/buy")
    private @ResponseBody
    BuyResponseDto buy(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                       @RequestBody @Valid PurchaseDto purchaseDto) {
        return vendingMachineService.buy(userDetailsDto, purchaseDto);
    }

    @PostMapping("/reset")
    private @ResponseBody
    User reset(@AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        return vendingMachineService.reset(userDetailsDto);
    }

}
