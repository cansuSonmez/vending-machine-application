package com.exercise.vendingmachine.service;

import com.exercise.vendingmachine.dto.ProductDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.Product;

public interface ProductService {

    Product createProduct(UserDetailsDto userDetailsDto, ProductDto productDto);

    Product getProduct(Long productId);

    Product updateProduct(UserDetailsDto userDetailsDto, Long productId, ProductDto productDto);

    Product deleteProduct(UserDetailsDto userDetailsDto, Long productId);

}
