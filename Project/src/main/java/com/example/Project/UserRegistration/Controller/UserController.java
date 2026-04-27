package com.example.Project.UserRegistration.Controller;

import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @PostMapping("/regis")
    public String res(
            @RequestParam String userName,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam long phNo,
            @RequestParam String city,
            @RequestParam int pincode,
            @RequestParam String state,
            @RequestParam String country){

        User user=new User();
        user.setUserName(userName);
        user.setPassword(encoder.encode(password));
        user.setEmail(email);
        user.setPhNo(phNo);
        user.setCity(city);
        user.setPincode(pincode);
        user.setState(state);
        user.setCountry(country);

        userService.saveUsers(user);
        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        if(userService.isLogin(email, password)){
            return "User LoggedIn";
        }
        return "Invalid Credentials";
    }
}


