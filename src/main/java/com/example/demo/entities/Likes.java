package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer likes;
    @ManyToOne
    @JoinColumn(name = " comment_id" , referencedColumnName = "commentId")
    private Comment comment;
}
