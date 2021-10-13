package com.example.x_ray.controller;

import com.example.x_ray.dto.comment.CommentDto;
import com.example.x_ray.dto.comment.RequestCommentDto;
import com.example.x_ray.dto.comment.ResponseCommentDto;
import com.example.x_ray.service.comment.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class CommentController {

    final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    public ResponseCommentDto commentDtoToResponseMapper(CommentDto commentDto){
        return new ResponseCommentDto(
                commentDto.getContent(),
                commentDto.getUserNickName(),
                commentDto.getPostId()
        );
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<ResponseCommentDto>> getCommentsByPostId(@PathVariable Long postId){

        List<CommentDto> commentByPostId = commentService.getCommentByPostId(postId);
        List<ResponseCommentDto> response = commentByPostId.stream().map(v -> commentDtoToResponseMapper(v)).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseCommentDto> addComment(@RequestBody RequestCommentDto requestCommentDto){
        CommentDto commentDto = new CommentDto(
                requestCommentDto.getContent(),
                requestCommentDto.getUserNickName(),
                requestCommentDto.getPostId()
        );
        CommentDto resultCommentDto = commentService.addCommentToPost(commentDto);
        if(resultCommentDto != null){
            ResponseCommentDto responseCommentDto = commentDtoToResponseMapper(resultCommentDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseCommentDto);
        }
        return null;
    }

    @DeleteMapping("/comment/id/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        log.info("commentId = [{}]", commentId);
        commentService.deleteComment(commentId);
    }

}
