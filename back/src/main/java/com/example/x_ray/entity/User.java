package com.example.x_ray.entity;


import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
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

}
