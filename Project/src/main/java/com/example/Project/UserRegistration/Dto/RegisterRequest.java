package com.example.Project.UserRegistration.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;
    private long phNo;
    private String city;
    private int pincode;
    private  String state;
    private  String country;
    private String role;

}
