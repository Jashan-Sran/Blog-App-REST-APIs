package com.geggitech.springboot.controller;

import com.geggitech.springboot.payload.CommentDto;
import com.geggitech.springboot.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {


    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.createComment(id,commentDto);

        return new ResponseEntity<CommentDto>(commentDto1, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}/comments")
    public List<CommentDto> getAllCommentsByPostId(@PathVariable Long id){
        return commentService.getAllCommentsByPostId(id);
    }

    @GetMapping("/posts/comment/{id}")
    public CommentDto getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @DeleteMapping("/posts/comment/{id}")
    public String deleteById(@PathVariable Long id){

        commentService.deleteCommentById(id);
        return "Comment deleted successfully with id : " + id;
    }

    @PutMapping("posts/comment/{id}")
    public CommentDto updateCommentById(@PathVariable Long id, @Valid @RequestBody CommentDto commentDto){
        return commentService.updateCommentById(id,commentDto);
    }

}
