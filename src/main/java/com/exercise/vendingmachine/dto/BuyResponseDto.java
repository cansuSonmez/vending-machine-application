package com.exercise.vendingmachine.dto;

import com.exercise.vendingmachine.model.Purchase;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BuyResponseDto {

    private Long totalSpent;

    private List<Purchase> purchases;

}
