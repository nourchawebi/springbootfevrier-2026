package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="firstname")
    private String firstname;
    private String lastname;
    @Column(nullable=false, unique=true,length=30)
    private String email;
    private String password;
    private String adress;
    @Column(nullable=false, unique=true)
    private String username;
    private String confirmpassword;



}
