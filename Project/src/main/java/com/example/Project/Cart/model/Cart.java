package com.example.Project.Cart.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data               // Adds Getters, Setters, toString, Equals, and Hashcode
@NoArgsConstructor  // Required by JPA
@AllArgsConstructor // Required for @Builder and manual creation
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;

    private int productId;
    private String Description;
    private boolean InStock;
    private int quantity;
    private int ListPrice;
    private int totCost;

}

