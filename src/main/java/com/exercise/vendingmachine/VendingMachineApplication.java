package com.exercise.vendingmachine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VendingMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendingMachineApplication.class, args);
    }

    /*@Bean
    CommandLineRunner initializeDatabase(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product(1L,10, new BigInteger("99"),"Coca Cola",1001110L));
            productRepository.save(new Product(2L,44, new BigInteger("99"),"Fanta",1001110L));
            productRepository.save(new Product(3L,23, new BigInteger("99"),"Sprite",1001110L));
            productRepository.save(new Product(4L,98, new BigInteger("199"),"Red Bull",1001110L));
            productRepository.save(new Product(5L,55, new BigInteger("341"),"Nestle",1002220L));
            productRepository.save(new Product(6L,32, new BigInteger("213"),"Milka",1002220L));
            productRepository.save(new Product(7L,49, new BigInteger("500"),"Toblerone",1002220L));
            productRepository.save(new Product(8L,22, new BigInteger("230"),"Kinder",1002220L));
        };
    }*/

}
