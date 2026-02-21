package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name="users")
public class UserEntity  implements UserDetails {
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
//@ManyToMany
   // private Set<Role> role;
   // @JoinTable(name="userrole", joinColumns = @JoinColumn(name="email"), inverseJoinColumns = @JoinColumn(name="idrole"))
@ManyToOne
@JoinColumn(name="role_id")
private Role role;
@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , fetch =  FetchType.LAZY)
    private List<Post> posts;
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="likeId" , referencedColumnName = "id")
    private Likes likes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
