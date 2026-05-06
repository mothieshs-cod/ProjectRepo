package com.example.Project.UserRegistration.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int userId;

    @NotNull(message = "Name is required")
    private  String userName;

    @NotNull(message = "Password is required")
    private String password;

    @Column(unique=true)
    @NotNull(message = "Email ID is required")
    private  String email;

    @NotNull(message = "Phone Number is required")
    private long phNo;

    @NotNull(message = "Address is required")
    private  String address;

    @NotNull(message = "City is required")
    private String city;

    @NotNull(message = "Pin code is required")
    private int pincode;

    @NotNull(message = "State is required")
    private  String state;

    @NotNull(message = "Country is required")
    private  String country;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}