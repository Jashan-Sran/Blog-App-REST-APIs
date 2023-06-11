package com.geggitech.springboot.service;

import com.geggitech.springboot.entity.Comment;
import com.geggitech.springboot.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CommentService {

    public CommentDto createComment(Long id, CommentDto commentDto);

    public List<CommentDto> getAllCommentsByPostId(Long id);

    public CommentDto getCommentById(Long id);

    public void deleteCommentById(Long id);

    public CommentDto updateCommentById(Long id, CommentDto commentDto);



}
