package com.javatechie.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("UserCredential")
public class UserCredential {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
}
