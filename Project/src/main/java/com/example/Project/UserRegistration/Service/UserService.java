package com.example.Project.UserRegistration.Service;

import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Component
public class UserService {




    @Autowired
    UserRepo repo;



    public List<User>getUsers (){
       return repo.findAll();
    }


    public void saveUsers(User user) {
        repo.save(user);
    }
}
