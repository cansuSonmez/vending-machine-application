package com.exercise.vendingmachine.service.impl;

import com.exercise.vendingmachine.dto.BuyResponseDto;
import com.exercise.vendingmachine.dto.DepositDto;
import com.exercise.vendingmachine.dto.PurchaseDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.advice.exception.EntityNotFoundException;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.model.Purchase;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.repository.PurchaseRepository;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.VendingMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class VendingMachineServiceImpl implements VendingMachineService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final PurchaseRepository purchaseRepository;

    public VendingMachineServiceImpl(
            UserRepository userRepository,
            ProductRepository productRepository,
            PurchaseRepository purchaseRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    @Transactional
    public User deposit(UserDetailsDto userDetailsDto, DepositDto depositDto) {
        User user = userRepository.findById(userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        if (user.getDeposit() == null) {
            user.setDeposit(0L);
        }
        user.setDeposit(user.getDeposit() + depositDto.getCoin().getCents());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User reset(UserDetailsDto userDetailsDto) {
        User user = userRepository.findById(userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        user.setDeposit(0L);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public BuyResponseDto buy(UserDetailsDto userDetailsDto, PurchaseDto purchaseDto) {
        Product product = productRepository.findById(purchaseDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
        if (product.getAmountAvailable() < purchaseDto.getAmount()) {
            throw new EntityNotFoundException("Not enough stock available");
        }

        User user = userRepository.findById(userDetailsDto.getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        final Long oldDeposit = user.getDeposit();
        Long totalCost = (long) product.getCost() * purchaseDto.getAmount();
        if (user.getDeposit() < totalCost) {
            throw new EntityNotFoundException("Not enough balance");
        }

        user.setDeposit(user.getDeposit() - totalCost);
        user = userRepository.save(user);

        product.setAmountAvailable(product.getAmountAvailable() - purchaseDto.getAmount());
        productRepository.save(product);

        Purchase purchase = Purchase.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .productId(product.getId())
                .unitCost(product.getCost())
                .productName(product.getProductName())
                .sellerId(product.getSellerId())
                .purchaseAmount(purchaseDto.getAmount())
                .totalCost(totalCost)
                .oldDeposit(oldDeposit)
                .newDeposit(user.getDeposit())
                .build();
        purchaseRepository.save(purchase);

        List<Purchase> purchases = purchaseRepository.findByUserId(user.getId());
        long totalSpent = purchases.stream().mapToLong(Purchase::getTotalCost).sum();

        return BuyResponseDto.builder()
                .totalSpent(totalSpent)
                .purchases(purchases)
                .build();
    }

}
