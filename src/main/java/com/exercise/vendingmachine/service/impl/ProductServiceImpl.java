package com.exercise.vendingmachine.service.impl;

import com.exercise.vendingmachine.dto.ProductDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.advice.exception.EntityNotFoundException;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(UserDetailsDto userDetailsDto, ProductDto productDto) {
        Product product = Product.builder()
                .amountAvailable(productDto.getAmountAvailable())
                .cost(productDto.getCost())
                .productName(productDto.getProductName())
                .sellerId(userDetailsDto.getUser().getId())
                .build();
        return this.productRepository.save(product);
    }

    /*
     * Get product can be called by both (all) seller or buyer accounts
     */
    @Override
    public Product getProduct(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    }

    @Override
    @Transactional
    public Product updateProduct(UserDetailsDto userDetailsDto, Long productId, ProductDto productDto) {
        Product product = this.productRepository.findByIdAndSellerId(productId, userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        product.setAmountAvailable(productDto.getAmountAvailable());
        product.setCost(productDto.getCost());
        product.setProductName(productDto.getProductName());
        return this.productRepository.save(product);
    }

    @Override
    @Transactional
    public Product deleteProduct(UserDetailsDto userDetailsDto, Long productId) {
        Product product = this.productRepository.findByIdAndSellerId(productId, userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        this.productRepository.delete(product);
        return product;
    }

}
