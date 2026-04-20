package com.example.Project.UserRegistration.Controller.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private  int userid;
    private  String username;
    private String userpassword;
    private  String email;
    private long Phno;
    private String city;
    private  String state;
    private  String country;

}
