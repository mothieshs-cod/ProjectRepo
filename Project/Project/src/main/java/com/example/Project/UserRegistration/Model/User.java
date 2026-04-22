package com.example.Project.UserRegistration.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
