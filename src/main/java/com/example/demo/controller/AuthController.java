package com.example.demo.controller;

import com.example.demo.DTO.AuthResponseDTO;
import com.example.demo.DTO.LoginDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.RoleName;
import com.example.demo.entities.UserEntity;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.security.JWTGenerator;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTGenerator jwtGenerator;
    private final RoleRepo roleRepo;

    public AuthController(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, JWTGenerator jwtGenerator, RoleRepo roleRepo) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
        this.roleRepo = roleRepo;
    }
    @PostConstruct
    public void createDefaultAdminAccount() {
        if(! userRepo.existsByUsername("admin")){
            UserEntity adminUser = new UserEntity();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setEmail("admin@demo.com");
            adminUser.setFirstname("admin");
            adminUser.setLastname("Demo");
            adminUser.setAdress("tunis");
            Role adminRole = roleRepo.findByRolename(RoleName.ADMIN).orElseGet(()->{
                Role newRole = new Role();
                newRole.setRolename(RoleName.ADMIN);
                return roleRepo.save(newRole);
            });
            adminUser.setRole(adminRole);
            userRepo.save(adminUser);
        }
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsername(),
                            loginDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails= (UserDetails) authentication.getPrincipal();
            String token = jwtGenerator.generateToken(authentication);
            UserEntity user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
            AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, user);
            return new ResponseEntity<>(authResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

}
