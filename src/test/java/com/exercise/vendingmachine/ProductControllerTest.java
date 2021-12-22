package com.exercise.vendingmachine;

import com.exercise.vendingmachine.controller.ProductController;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.repository.ProductRepository;
import com.exercise.vendingmachine.serviceTest.ProductServiceImplTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

@WebMvcTest(ProductController.class)
@WithMockUser("ahmet")
public class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductRepository productRepository;

    @MockBean
    ProductServiceImplTest productServiceImplTest;

    Product RECORD_1 = new Product(1L,10, 99,"Coca Cola",1001110L);
    Product RECORD_2 = new Product(2L,44, 99,"Fanta",1001110L);

    @Test
    public void getProductById_success() throws Exception {
        Mockito.when(productRepository.findById(RECORD_1.getId())).thenReturn(Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void createProductById_success() throws Exception {
        Product record = Product.builder()
                .id(2L)
                .amountAvailable(35)
                .cost(33)
                .productName("Coca Cola")
                .sellerId(1001110L)
                .build();

        Mockito.when(productRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProductById_success() throws Exception {
        Mockito.when(productRepository.findById(RECORD_2.getId())).thenReturn(Optional.of(RECORD_2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/product/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductRecord_recordNotFound() throws Exception {
        Product updatedRecord = Product.builder()
                .id(2L)
                .amountAvailable(35)
                .cost(33)
                .productName("Coca Cola")
                .sellerId(1001110L)
                .build();

        Mockito.when(productRepository.findById(updatedRecord.getId())).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertEquals("Patient with ID 5 does not exist.",
                                Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}
