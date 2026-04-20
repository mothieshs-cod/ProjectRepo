package com.example.Project.UserRegistration.Controller.Service;

import com.example.Project.UserRegistration.Controller.Model.User;
import com.example.Project.UserRegistration.Controller.Model.UserPrincipal;
import com.example.Project.UserRegistration.Controller.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo repo;

    public List<User> getusers(){
        return repo.findAll();
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        User user=repo.findByusername(username);
        if(user==null){
            System.out.println("404 error");
            throw new UsernameNotFoundException("User 404");
        }
        return new UserPrincipal(user);


    }


}
