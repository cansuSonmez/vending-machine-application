package com.exercise.vendingmachine.dto;

import com.exercise.vendingmachine.enumeration.CoinEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DepositDto {

    @NotNull
    private CoinEnum coin;

}
