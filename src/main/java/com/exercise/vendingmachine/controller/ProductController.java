package com.exercise.vendingmachine.controller;

import com.exercise.vendingmachine.dto.ProductDto;
import com.exercise.vendingmachine.dto.UserDetailsDto;
import com.exercise.vendingmachine.model.Product;
import com.exercise.vendingmachine.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(
        value = "/products",
        produces= MediaType.APPLICATION_JSON_VALUE,
        consumes=MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    private @ResponseBody
    Product createProduct(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
            @RequestBody @Valid ProductDto productDto) {
        return productService.createProduct(userDetailsDto, productDto);
    }

    /*
     * Get product can be called by both (all) seller or buyer accounts
     */
    @GetMapping("/{productId}")
    private @ResponseBody
    Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @PutMapping("/{productId}")
    private @ResponseBody
    Product updateProduct(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                          @PathVariable Long productId, @RequestBody @Valid ProductDto productDto) {
        return productService.updateProduct(userDetailsDto, productId, productDto);
    }

    @DeleteMapping("/{productId}")
    private @ResponseBody
    Product deleteProduct(@AuthenticationPrincipal UserDetailsDto userDetailsDto,
                          @PathVariable Long productId) {
        return productService.deleteProduct(userDetailsDto, productId);
    }

}
