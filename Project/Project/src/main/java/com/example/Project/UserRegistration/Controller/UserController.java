package com.example.Project.UserRegistration.Controller;


import com.example.Project.UserRegistration.Controller.Model.User;
import com.example.Project.UserRegistration.Controller.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/data")
    public List<User> getUSer(){
        return  userService.getUsers();
    }


    @PostMapping("/regis")
    public String res(
            @RequestParam String username,
                      @RequestParam String password,
                      @RequestParam String email,
                      @RequestParam long Phno,
                      @RequestParam String city,
                      @RequestParam String state,
                      @RequestParam String country){

        User user=new User();
        user.setUsername(username);
        user.setUserpassword(password);
        user.setEmail(email);
        user.setPhno(Phno);
        user.setCity(city);
        user.setState(state);
        user.setCountry(country);

        userService.saveUsers(user);
        return "User Registered Successfully";
    }







}



