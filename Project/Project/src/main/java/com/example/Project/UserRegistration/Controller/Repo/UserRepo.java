package com.example.Project.UserRegistration.Controller.Repo;

import com.example.Project.UserRegistration.Controller.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByusername(String username);
}
