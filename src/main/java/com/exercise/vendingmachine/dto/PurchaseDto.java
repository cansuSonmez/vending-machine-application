package com.exercise.vendingmachine.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class PurchaseDto {

    @NotNull
    private Long productId;

    @NotNull
    @Range(min=1, max=Integer.MAX_VALUE)
    private Integer amount;

}
