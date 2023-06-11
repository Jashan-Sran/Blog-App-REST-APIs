package com.geggitech.springboot.service.Impl;

import com.geggitech.springboot.entity.Comment;
import com.geggitech.springboot.entity.Post;
import com.geggitech.springboot.exception.ResourceNotFound;
import com.geggitech.springboot.payload.CommentDto;
import com.geggitech.springboot.repository.CommentRepository;
import com.geggitech.springboot.repository.PostRepository;
import com.geggitech.springboot.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {


    private CommentRepository commentRepository;

    private ModelMapper modelMapper;

    private PostRepository postRepository;

    public CommentServiceImpl( CommentRepository commentRepository,PostRepository postRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public CommentDto createComment(Long id, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFound("Post","id",id));

        comment.setPost(post);

        Comment comment1 = commentRepository.save(comment);

        CommentDto commentDto1 = mapToDto(comment1);

        return commentDto1;
    }

    @Override
    public List<CommentDto> getAllCommentsByPostId(Long id) {

        List<Comment> comments = commentRepository.findByPostId(id);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).get();
        return mapToDto(comment);
    }

    @Override
    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentDto updateCommentById(Long id, CommentDto commentDto) {

        Comment comment = commentRepository.findById(id).get();

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }


//    method to convert Entity to DTO

    private CommentDto mapToDto(Comment comment){

        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);

//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return commentDto;
    }

//    method to convert DTO to Entity

    private Comment mapToEntity(CommentDto commentDto){

        Comment comment = modelMapper.map(commentDto,Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }

}
