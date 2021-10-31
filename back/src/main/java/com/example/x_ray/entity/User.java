package com.example.x_ray.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.crypto.Mac;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickName;

    private String password;
    private String email;
    private Date date;


    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public User(String nickName, String password, String email, Date date, List<Post> posts) {
        this.nickName = nickName;
        this.password = password;
        this.email = email;
        this.date = date;
        this.posts = posts;
    }

    public User() {

    }
}
