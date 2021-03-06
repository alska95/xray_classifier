package com.example.x_ray.dto.image;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ImageDto {
    private String userNickName;
    private String originalImageFileName;
    private String heatmapImageFileName;
    private Date createdDate;
    private String diagnosisResult;
    private String diseaseName;

    public ImageDto(String userNickName){
        this.userNickName = userNickName;
    }

    public ImageDto(String userNickName, String originalImageFileName, String heatmapImageFileName, Date createdDate, String diagnosisResult) {
        this.userNickName = userNickName;
        this.originalImageFileName = originalImageFileName;
        this.heatmapImageFileName = heatmapImageFileName;
        this.createdDate = createdDate;
        this.diagnosisResult = diagnosisResult;
    }

    public ImageDto(String originalImageFileName, String heatmapImageFileName, Date createdDate) {
        this.originalImageFileName = originalImageFileName;
        this.heatmapImageFileName = heatmapImageFileName;
        this.createdDate = createdDate;
    }

    public ImageDto(String userNickName, String originalImageFileName, String heatmapImageFileName, Date createdDate) {
        this.userNickName = userNickName;
        this.originalImageFileName = originalImageFileName;
        this.heatmapImageFileName = heatmapImageFileName;
        this.createdDate = createdDate;
    }
}
