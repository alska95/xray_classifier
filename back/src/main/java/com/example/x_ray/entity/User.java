package com.example.x_ray.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String password;
    private String email;


    @OneToMany(mappedBy = "image_id")
    private List<Image> images;

}
