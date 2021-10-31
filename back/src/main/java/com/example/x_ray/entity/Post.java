package com.example.x_ray.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String content;

    private String result;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    public Post(String content, String result, User user, List<Comment> comments, Image image) {
        this.content = content;
        this.result = result;
        this.user = user;
        this.comments = comments;
        this.image = image;
    }

    public Post() {

    }
}
