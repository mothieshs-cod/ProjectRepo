package com.example.Project.UserRegistration.Service;

import com.example.Project.UserRegistration.Dto.RegisterRequest;
import com.example.Project.UserRegistration.Model.Role;
import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Repository.UserRepository;
import com.example.Project.UserRegistration.Security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder encoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("An account with this email already exists.");
        }

        Role role = null;

        if(request.getRole() == null){
            role = Role.ROLE_USER;
        }
        else if (request.getRole().equalsIgnoreCase("ROLE_ADMIN")) {
            role = Role.ROLE_ADMIN;
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setPhNo(request.getPhNo());
        user.setCity(request.getCity());
        user.setPincode(request.getPincode());
        user.setState(request.getState());
        user.setCountry(request.getCountry());
        user.setRole(role);

        userRepository.save(user);
        return "User registered successfully.";
    }

    public String login(String email, String password) {

        userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(
                        "No account found with this email."));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid password.");
        }

        return jwtUtil.generateToken(email);
    }
}