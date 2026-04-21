package com.example.Project.Controller;

import com.example.Project.Model.User;
import com.example.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping("/data")
    public List<User> getUSer(){
        return  userService.getUsers();
    }

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
        String password = loginData.get("password"); // Keep keys simple in Postman

        if(userService.isLogin(email, password)){
            return "User LoggedIn";
        }
        return "Invalid Credentials";
    }
}



