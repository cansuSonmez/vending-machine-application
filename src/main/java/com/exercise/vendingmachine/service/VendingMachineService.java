package com.exercise.vendingmachine.service;

import com.exercise.vendingmachine.dto.BuyResponseDto;
import com.exercise.vendingmachine.dto.DepositDto;
import com.exercise.vendingmachine.dto.PurchaseDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.User;

public interface VendingMachineService {

    User deposit(UserDetailsDto userDetailsDto, DepositDto depositDto);

    User reset(UserDetailsDto userDetailsDto);

    BuyResponseDto buy(UserDetailsDto userDetailsDto, PurchaseDto purchaseDto);

}
