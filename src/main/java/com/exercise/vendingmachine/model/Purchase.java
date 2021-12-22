package com.exercise.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(name = "userId_IX", columnList = "userId"))
@Data
@Builder
@AllArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 64)
    public String username;

    @Column(nullable = false)
    private Long productId;

    public Integer unitCost;

    @Column(length = 64)
    public String productName;

    @Column(nullable = false)
    public Long sellerId;

    private Integer purchaseAmount;

    public Long totalCost;

    public Long oldDeposit;

    public Long newDeposit;

    public Purchase() { }

}
