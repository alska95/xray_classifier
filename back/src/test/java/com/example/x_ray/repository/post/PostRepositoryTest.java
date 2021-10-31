package com.example.x_ray.repository.post;

import com.example.x_ray.dto.post.PostSearchConditionDto;
import com.example.x_ray.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;


    @Test
    public void 검색테스트(){
        PostSearchConditionDto conditionName = new PostSearchConditionDto(
                "all",
                "hwang",
                "hwang",
                null,
                null
        );

        List<Post> byCondition = postRepository.findByCondition(conditionName);
        for (Post post : byCondition) {
            System.out.println("post = " + post.getContent());
            System.out.println("post.getImage().getDiseaseName() = " + post.getImage().getDiseaseName());
        }



        Assertions.assertThat(byCondition.get(0).getUser()).isEqualTo("hwang");
        System.out.println("==================================");

        PostSearchConditionDto conditionDisease = new PostSearchConditionDto(
                "all",
                "Cardiomegaly",
                "Cardiomegaly",
                null,
                null
        );
        List<Post> byDisease = postRepository.findByCondition(conditionDisease);
        for (Post post : byDisease) {
            System.out.println("post = " + post.getContent());
            System.out.println("post.getImage().getDiseaseName() = " + post.getImage().getDiseaseName());
        }
        Assertions.assertThat(byCondition.get(0).getImage().getDiseaseName()).isEqualTo("Cardiomegaly");

    }

}