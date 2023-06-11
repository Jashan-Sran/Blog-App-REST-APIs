package com.geggitech.springboot.service;

import com.geggitech.springboot.entity.Post;
import com.geggitech.springboot.payload.PostDto;
import com.geggitech.springboot.payload.PostResponse;

import java.util.List;

public interface PostService {

    public PostDto createPost(PostDto postDto);

    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    public PostDto getPostById(Long id);

    public PostDto updatePost( PostDto postDto,Long id);

    public String deletePostById(Long id);

    List<PostDto> getPostsByCategoryId(Long categoryId);

    List<Post> getPostByTitle(String query);

}
