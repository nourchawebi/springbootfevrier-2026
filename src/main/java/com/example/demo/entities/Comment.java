package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private Date created;
    private String body ;
    private String attribute4;
    @ManyToOne
    private Post post;
    @OneToMany(mappedBy = "comment" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Likes> likes;
}
