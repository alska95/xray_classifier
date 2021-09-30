package com.example.x_ray.service;

import org.springframework.stereotype.Service;

@Service
public interface ImageService {
    public String saveImageName(String [] fileNames);
    public String[] getImageName();
}
