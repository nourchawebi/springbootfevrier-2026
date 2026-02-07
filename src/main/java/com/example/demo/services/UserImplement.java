package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.interfaces.UserInterface;
import com.example.demo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserImplement implements UserInterface {
   @Autowired
    UserRepo  userRepo;
    @Override
    public UserEntity adduser(UserEntity users) {
        return userRepo.save(users);
    }

    @Override
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public List<UserEntity> addListUsers(List<UserEntity> users) {
        return   userRepo.saveAll(users);
    }

    @Override
    public String addUerWTCHP(UserEntity users) {
        String ch="";
        if(users.getPassword().equals(users.getConfirmpassword())){
            userRepo.save(users);
            ch=" user added successfully";
        }
        else{
            ch="user Password Mismatch";
        }
        return ch;
    }

    @Override
    public String addUserWTUN(UserEntity users) {
        String ch="";
        if( userRepo.existsByUsername(users.getUsername())){
            ch=" user already exists";
        }else{
            userRepo.save(users);
            ch="user added successfully";
        }
        return ch;
    }
 private UserEntity user ;
    private Long id;

    @Override
    public UserEntity UpdateUser(UserEntity users, Long id) {
        this.user= users;
        this.id=id;
         UserEntity u = userRepo.findById(id).orElse(null);
           u.setFirstname(user.getFirstname());
           u.setLastname(user.getLastname());
           return userRepo.save(u);

    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public UserEntity getUserByName(String name) {
        Optional<UserEntity> user = userRepo.findByUsername(name);
        if(user.isPresent()){
            return user.get();
        } else {return null;}
      //  return userRepo.findByUsername(name).orElse(null);
    }

    @Override
    public List<UserEntity> getUserSWT(String un) {
      return userRepo.findByCle(un);
    }

    @Override
    public List<UserEntity> getUsersByEmailDomaine(String emailDomaine) {
        return userRepo.findByDomain(emailDomaine);
    }
}
