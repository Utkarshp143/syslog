//package com.advantal.userlog.services;
//
//import com.advantal.userlog.model.User;
//import com.advantal.userlog.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository repository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtService jwtService;
//
//    public String saveUser(User credential) {
//        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//        repository.save(credential);
//        return "user added to the system";
//    }
//
//    public String generateToken(String username) {
//        return jwtService.generateToken(username);
//    }
//
//    public void validateToken(String token) {
//        jwtService.validateToken(token);
//    }
//
//
//}
