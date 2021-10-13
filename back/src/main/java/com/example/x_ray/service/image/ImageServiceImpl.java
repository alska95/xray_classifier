package com.example.x_ray.service.image;

import com.example.x_ray.ConstVariable;
import com.example.x_ray.dto.image.ImageDto;
import com.example.x_ray.dto.image.ResponseImageDto;
import com.example.x_ray.entity.Image;
import com.example.x_ray.repository.image.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {

        this.imageRepository = imageRepository;
    }

    @Transactional
    @Override
    public void saveImageName(List<MultipartFile> images , ImageDto imageDto) {

        class ResultSet{
            int index;
            double result;

            public ResultSet(int index, double result) {
                this.index = index;
                this.result = result;
            }
        }
        String result = imageDto.getDiagnosisResult();
        String[] splitResult = result.split(",");
        ArrayList<ResultSet> resultList = new ArrayList<>();
        for(int i = 0; i < 14; i++){
            double target= Double.parseDouble(splitResult[i]);
            if(target > ConstVariable.THRESHOLD[i]){
                resultList.add(new ResultSet(i, target));
            }
        }
        Collections.sort(resultList, new Comparator<ResultSet>(){
            @Override
            public int compare(ResultSet o1, ResultSet o2) {
                return (int)(o2.result- o1.result);
            }
        });

        Date date = imageDto.getCreatedDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = format.format(date);

        StringBuilder sb = new StringBuilder();
        sb.append("/img/");
        if(resultList.size() > 0){
            sb.append(ConstVariable.DISEASE_LABEL[resultList.get(0).index]);
            sb.append("/");
            for(int i = 0 ;i < resultList.size() ; i++){
                sb.append(ConstVariable.DISEASE_LABEL[resultList.get(i).index]);
                sb.append("_");
            }
        }else{
            sb.append("/noFound/");
        }


        sb.append(dateString);

        String imageNames[] = new String[2];
        String saveDir = "I:\\programming\\xray_classifier\\front\\public";
        imageNames[0] = sb.toString()+images.get(0).getOriginalFilename();
        imageNames[1] = sb.toString()+images.get(1).getOriginalFilename();
        imageDto.setOriginalImageFileName(imageNames[0]);
        imageDto.setHeatmapImageFileName(imageNames[1]);

        log.info("original Image = [{}] ", imageNames[0]);
        log.info("heatMap Image = [{}] ", imageNames[1]);

        String fileNames[] = new String[2]; // 0: original 1: heatmap

        for(int i = 0 ; i < 2 ; i++){
            fileNames[i] = saveDir+imageNames[i];
            log.info("fileNames [{}] = [{}]", i , fileNames[i]);
            try{
                images.get(i).transferTo(new File(fileNames[i]));
            }catch (IllegalStateException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();;
            }
        }

        imageRepository.saveImages(imageDto);

    }

    @Transactional
    @Override
    public ImageDto getImageByImageName(String originalImageName) {
        Image image = imageRepository.getImage(originalImageName);
        ImageDto dto  = new ImageDto(
                image.getUser().getNickName(),
                image.getOriginalImageFileName(),
                image.getHeatmapImageFileName(),
                image.getCreatedDate()
        );
        return dto;
    }
}
