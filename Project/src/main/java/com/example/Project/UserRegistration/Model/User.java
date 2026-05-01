package com.example.Project.UserRegistration.Model;

import jakarta.persistence.*;
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
    private  String userName;
    private String password;
    @Column(unique=true)
    private  String email;
    private long phNo;
    private String city;
    private int pincode;
    private  String state;
    private  String country;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Role> roles = new HashSet<>();
}