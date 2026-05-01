package com.example.Project.UserRegistration.Service;

import com.example.Project.UserRegistration.Dto.RegisterRequest;
import com.example.Project.UserRegistration.Model.Role;
import com.example.Project.UserRegistration.Model.User;
import com.example.Project.UserRegistration.Repository.RoleRepository;
import com.example.Project.UserRegistration.Repository.UserRepository;
import com.example.Project.UserRegistration.Security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder encoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("An account with this email already exists.");
        }

        Set<Role> resolvedRoles = new HashSet<>();

        if (request.getRoles() == null || request.getRoles().isEmpty()) {

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role r = new Role();
                        r.setName("ROLE_USER");
                        return roleRepository.save(r);
                    });
            resolvedRoles.add(userRole);
        } else {
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findByName(roleName)
                        .orElseGet(() -> {
                            Role r = new Role();
                            r.setName(roleName);
                            return roleRepository.save(r);
                        });
                resolvedRoles.add(role);
            }
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
        user.setRoles(resolvedRoles);

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