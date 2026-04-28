package com.example.Project.Cart.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Integer userId;

    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;

    private String fullName;
    private String address;
    private String mobileNumber;
    private String city;
    private String pincode;
    private String state;
    private String country;

    private String cardType;
    private String cardNumber;
    private String expiryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}