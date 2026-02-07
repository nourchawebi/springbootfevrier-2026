package com.example.demo.interfaces;

import com.example.demo.entities.UserEntity;

import java.util.List;

public interface UserInterface {
    UserEntity adduser(UserEntity users);
    void deleteUser(Long id);
    List<UserEntity> addListUsers(List<UserEntity> users);
    String addUerWTCHP(UserEntity users);
    String addUserWTUN(UserEntity users);
    UserEntity UpdateUser(UserEntity users, Long id);
    List<UserEntity> getAllUsers();
    UserEntity getUserById(Long id);
    UserEntity getUserByName(String name);
    List<UserEntity> getUserSWT(String un);
    List<UserEntity> getUsersByEmailDomaine(String emailDomaine);

}
