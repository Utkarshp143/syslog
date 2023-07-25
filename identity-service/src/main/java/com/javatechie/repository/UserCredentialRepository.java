package com.javatechie.repository;

import com.javatechie.entity.UserCredential;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCredentialRepository  extends MongoRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);
}
