package com.example.demo.controller;

import com.example.demo.DTO.UserWithRoleRequest;
import com.example.demo.entities.UserEntity;
import com.example.demo.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("api/user")
@RestController
public class UserController {
    @Autowired
    UserInterface userinterface;
    @GetMapping("afficher")
    public String user(){
        return "user";
    }
    @GetMapping("users")
    public ResponseEntity<Map<String,Object>> getUser(){
        Map<String,Object> user = new HashMap<>();
        user.put("status","ok");
        List<String> users = new ArrayList<>();
        users.add("john");
        users.add("john");
        users.add("mary");
        users.add("jane");
        user.put("users",users);
        Set<String> users1 = new HashSet<>();
        users1.add("john");
        users1.add("john");
        users1.add("mary");
        users1.add("jane");
        users1.add("jane");
        users1.add("john");
        users1.add("mary");
        users1.add("jane");
        user.put("users1",users1);
 return ResponseEntity.ok(user);
    }
    @PostMapping("add")
    public UserEntity addUser( @RequestBody UserEntity user){

        return userinterface.adduser(user);
    }
    @DeleteMapping("delete/{id}")
    public void deleteUser1(@PathVariable Long id){
        userinterface.deleteUser(id);
    }
    @DeleteMapping("delete")
    public String deleteUser2(@RequestParam("a") Long id){
        userinterface.deleteUser(id);
        return "user deleted" ;
    }
    @PostMapping("saveall")
     public List<UserEntity> addListUsers(@RequestBody List<UserEntity> users){
        return userinterface.addListUsers(users);
    }
    @PostMapping("addwithconfpassword")
    public String addUserWithConfPassword(@RequestBody UserEntity user){
        return userinterface.addUerWTCHP(user);
    }
    @PostMapping("addWTUN")
    public String addUserWTUN(@RequestBody UserEntity user){
        return userinterface.addUserWTUN(user);
    }
    @PutMapping("updateuser/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity user){
        return userinterface.UpdateUser(user,id);
    }
     @GetMapping("all")
    public List<UserEntity> getAllUsers(){
        return userinterface.getAllUsers();
     }
     @GetMapping("findbyid/{id}")
    public UserEntity findUserById(@PathVariable Long id){
        return userinterface.getUserById(id);
     }
     @GetMapping("findbyusername/{abc}")
    public UserEntity findUserByUsername(@PathVariable("abc") String username){
        return userinterface.getUserByName(username);
     }
     @GetMapping("getuserswt/{cle}")
    public List<UserEntity> getUserSW(@PathVariable String cle){
        return userinterface.getUserSWT(cle);
     }
     @GetMapping("getuserbyemaildomaine")
    public List<UserEntity> getusersbyemaildomain(@RequestParam String email){
        return userinterface.getUsersByEmailDomaine(email);
     }
     @PostMapping("add-with-role")
    public UserEntity addUserWithRole(@RequestBody UserWithRoleRequest request){
        return userinterface.adduserwithrole( request.getUser(),request.getRole().getRolename());
     }
}
