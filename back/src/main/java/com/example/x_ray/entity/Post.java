package com.example.x_ray.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post_id")
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;


}
