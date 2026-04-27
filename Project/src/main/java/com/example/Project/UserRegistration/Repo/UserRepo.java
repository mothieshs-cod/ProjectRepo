package com.example.Project.UserRegistration.Repo;

import com.example.Project.UserRegistration.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByusername(String username);
}
