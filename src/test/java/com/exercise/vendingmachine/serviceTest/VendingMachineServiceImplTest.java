package com.exercise.vendingmachine.serviceTest;
import com.exercise.vendingmachine.dto.BuyResponseDto;
import com.exercise.vendingmachine.dto.DepositDto;
import com.exercise.vendingmachine.dto.PurchaseDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.enumeration.CoinEnum;
import com.exercise.vendingmachine.enumeration.UserRole;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.model.Purchase;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.repository.PurchaseRepository;
import com.exercise.vendingmachine.repository.UserRepository;
import com.exercise.vendingmachine.service.VendingMachineService;
import com.exercise.vendingmachine.service.impl.VendingMachineServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VendingMachineServiceImplTest {

    @InjectMocks
    VendingMachineServiceImpl vendingMachineService;

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    PurchaseRepository purchaseRepository;

    @Mock
    VendingMachineService service;

    User user = new User(1L, "cansu", "123456", 0L, UserRole.BUYER);
    Product product = Product.builder()
            .id(1L)
            .productName("kek")
            .amountAvailable(5)
            .cost(10)
            .sellerId(2L)
            .build();

    @Test
    public void testDeposit() throws Exception{
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        DepositDto depositDto = new DepositDto();
        depositDto.setCoin(CoinEnum.CENTS_5);

        User user1 = user;
        user.setDeposit(user.getDeposit() + depositDto.getCoin().getCents());

        when(userRepository.findById(userDetailsDto.getUser().getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        assertEquals(vendingMachineService.deposit(userDetailsDto, depositDto), user);

    }

    @Test
    public void testReset() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(userRepository.findById(userDetailsDto.getUser().getId())).thenReturn(Optional.ofNullable(user));

        User user1 = user;
        user1.setDeposit(0L);

        when(userRepository.save(user1)).thenReturn(user1);
        assertEquals(vendingMachineService.reset(userDetailsDto),user1);
    }

    @Test
    public void testBuy() throws Exception {
        User user1 = new User(1L, "cansu", "123456", 100L, UserRole.BUYER);
        UserDetailsDto userDetailsDto = new UserDetailsDto(user1);

        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setAmount(2);
        purchaseDto.setProductId(product.getId());

        Long oldDeposit = user1.getDeposit();
        Long totalCost = Long.valueOf(product.getCost() * purchaseDto.getAmount());

        User user2 = user1;
        user2.setDeposit(user1.getDeposit() - purchaseDto.getAmount() * product.getCost());

        Product product1 = product;
        product1.setAmountAvailable(product.getAmountAvailable() - purchaseDto.getAmount());

        when(productRepository.findById(purchaseDto.getProductId())).thenReturn(Optional.of(product));
        when(userRepository.findById(userDetailsDto.getUser().getId())).thenReturn(Optional.of(user1));
        when(userRepository.save(user1)).thenReturn(user1);
        when(productRepository.save(product1)).thenReturn(product1);

       Purchase purchase = Purchase.builder()
               .id(user1.getId())
               .username(user2.getUsername())
               .productId(product1.getId())
               .unitCost(product1.getCost())
               .productName(product1.getProductName())
               .sellerId(product1.getSellerId())
               .purchaseAmount(purchaseDto.getAmount())
               .totalCost(totalCost)
               .oldDeposit(oldDeposit)
               .newDeposit(user2.getDeposit())
               .build();

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase);

        when(purchaseRepository.findByUserId(user2.getId())).thenReturn(purchases);

        long totalSpent = purchases.stream().mapToLong(Purchase::getTotalCost).sum();

        BuyResponseDto buyResponseDto = BuyResponseDto.builder()
                .totalSpent(totalSpent)
                .purchases(purchases)
                .build();

        assertEquals(vendingMachineService.buy(userDetailsDto,purchaseDto),(buyResponseDto));

    }
}
