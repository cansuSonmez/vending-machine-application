package com.exercise.vendingmachine.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ProductDto {

    @NotNull(message = "this field is not null !")
    @Range(min=1, max=Integer.MAX_VALUE)
    public Integer amountAvailable;

    @NotNull
    @Range(min = 1)
    public Integer cost;

    @NotEmpty
    @Size(min = 2, max = 64)
    public String productName;

}
