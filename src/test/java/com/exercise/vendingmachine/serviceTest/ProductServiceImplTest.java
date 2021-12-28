package com.exercise.vendingmachine.serviceTest;

import com.exercise.vendingmachine.dto.ProductDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.enumeration.UserRole;
import com.exercise.vendingmachine.advice.exception.EntityNotFoundException;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.model.User;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.service.ProductService;
import com.exercise.vendingmachine.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductService productService;

    Product product1 = new Product(1L,10, 99,"Coca Cola",1L);
    Product product2 = new Product(2L,44, 99,"Fanta",2L);

    User user = new User(1L,"cansu", "123456", 1L, UserRole.SELLER);


    @Test
    public void testCreateProduct() throws EntityNotFoundException {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        ProductDto productDto = new ProductDto();
        productDto.setProductName("Water");
        productDto.setAmountAvailable(30);
        productDto.setCost(100);

        Product record = Product.builder()
                .id(null)
                .amountAvailable(productDto.getAmountAvailable())
                .cost(productDto.getCost())
                .productName(productDto.getProductName())
                .sellerId(userDetailsDto.getUser().getId())
                .build();

        when(productRepository.save(record)).thenReturn(record);
        assertEquals(productServiceImpl.createProduct(userDetailsDto, productDto), record);
    }

    @Test
    public void updateProductRecord_success() throws Exception {
        Product record = Product.builder()
                .id(2L)
                .amountAvailable(35)
                .cost(33)
                .productName("Coca Cola")
                .sellerId(1001110L)
                .build();

        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        ProductDto productDto = new ProductDto();
        productDto.setProductName("Coca Cola");
        productDto.setAmountAvailable(20);
        productDto.setCost(50);

        Product updateRecord = Product.builder()
                .id(2L)
                .amountAvailable(20)
                .cost(50)
                .productName("Coca Cola")
                .sellerId(1001110L)
                .build();

        Mockito.when(productRepository.findByIdAndSellerId(2L, userDetailsDto.getUser().getId()))
                .thenReturn(Optional.of(record));
        Mockito.when(productRepository.save(updateRecord)).thenReturn(updateRecord);
        assertEquals(productServiceImpl.updateProduct(userDetailsDto, 2L, productDto), updateRecord);
    }

    @Test
    public void deleteProductById_success() throws Exception {
        UserDetailsDto userDetailsDto = new UserDetailsDto(user);

        when(productRepository.findByIdAndSellerId(2L, userDetailsDto.getUser().getId())).thenReturn(Optional.ofNullable(product2));
        assertEquals(productServiceImpl.deleteProduct(userDetailsDto, 2L), product2);
    }

    @Test
    public void getProductById_success() throws Exception{
         given(productRepository.findById(product1.getId())).willReturn(Optional.of(product1));
         assertEquals(productServiceImpl.getProduct(1L),product1);

    }

}
