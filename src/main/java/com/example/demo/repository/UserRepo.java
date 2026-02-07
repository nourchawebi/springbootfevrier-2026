package com.example.demo.repository;

import com.example.demo.entities.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepo  extends JpaRepository<UserEntity,Long> {
    /* NAMED METHOD */
      boolean existsByUsername(String username);
      Optional<UserEntity> findByUsername(String username);
       List<UserEntity> findByEmailAndAdressStartsWith(String email, String adressStartsWith);
      UserEntity findByEmail(String email);
    /* JPQL METHOD */
    @Query("SELECT  u from UserEntity u  WHERE u.username=?1")
    UserEntity findByUsernameJPQL(String username);
    @Query("SELECT  CASE WHEN COUNT (u)>0 THEN true ELSE false end from UserEntity u WHERE u.username=:username")
    boolean existsByUsernameJPQL(@Param("username") String username);
    /*SQL METHOD */
     @Query(value ="SELECT * FROM users where username=?1" , nativeQuery = true)
    UserEntity findByUsernameSQL(String username) ;
     @Query(value="SELECT count(*) FROM users WHERE username=:u", nativeQuery = true)
    boolean existsByUsernameSQL(@Param("u") String username) ;
     @Query(value="select * from users where username  like :cle%", nativeQuery = true)
    List<UserEntity> findByCle(@Param("cle") String cle) ;
     @Query(value="select * from users where email like %:domain%" , nativeQuery = true)
    List<UserEntity> findByDomain(@Param("domain") String domain) ;


}
