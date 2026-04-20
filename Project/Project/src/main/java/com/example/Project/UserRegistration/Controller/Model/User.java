package com.example.Project.UserRegistration.Controller.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
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




    public User(){

    }


    public User(int userid,String username,String userpassword,String email,long phno,String city,String state,String country){
        this.userid=userid;
        this.username=username;
        this.userpassword=userpassword;
        this.email=email;
        this.Phno=phno;
        this.city=city;
        this.state=state;
        this.country=country;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public long getPhno() {
        return Phno;
    }

    public void setPhno(long phno) {
        Phno = phno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
