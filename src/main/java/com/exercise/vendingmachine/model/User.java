package com.exercise.vendingmachine.model;

import com.exercise.vendingmachine.enumeration.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    public String username;

    @Column(nullable = false)
    @JsonIgnore
    public String password;

    public Long deposit;

    @Enumerated(EnumType.STRING)
    public UserRole role;

    public User() {

    }
}