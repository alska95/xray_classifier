package com.example.x_ray.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class Image {
    @Id @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String originalImageFileName;
    private String heatmapImageFileName;
    private String diseaseName;
    private Date createdDate;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
