package com.example.Project.UserRegistration.Controller.Service;

import com.example.Project.UserRegistration.Controller.Model.User;
import com.example.Project.UserRegistration.Controller.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Component
public class UserService {

    @Autowired
    UserRepo repo;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public List<User>getUsers (){
       return repo.findAll();
    }

    public void saveUsers(User user) {
        repo.save(user);
    }

    public boolean isLogin(String email, String password) {
        User user = repo.findByEmail(email);
        if (user == null) return false;

        return encoder.matches(password, user.getUserpassword());
    }
}
