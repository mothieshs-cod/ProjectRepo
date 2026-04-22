package com.example.Project.UserRegistration.Service;

import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Model.UserPrincipal;
import com.example.Project.UserRegistration.Repo.UserRepo;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new UserPrincipal(user);
    }
}
