package com.example.Project.UserRegistration.Service;

import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
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

        return encoder.matches(password, user.getPassword());
    }
}
