package com.geggitech.springboot.service.Impl;

import com.geggitech.springboot.entity.Category;
import com.geggitech.springboot.entity.Post;
import com.geggitech.springboot.exception.ResourceNotFound;
import com.geggitech.springboot.payload.CategoryDto;
import com.geggitech.springboot.payload.PostDto;
import com.geggitech.springboot.payload.PostResponse;
import com.geggitech.springboot.repository.CategoryRepository;
import com.geggitech.springboot.repository.PostRepository;
import com.geggitech.springboot.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private ModelMapper modelMapper;

    private PostRepository postRepository;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFound("Category","id", postDto.getCategoryId()));

        Post post = mapToEntity(postDto);

        post.setCategory(category);

        Post newPost = postRepository.save(post);

        PostDto postDto1 = mapToDto(newPost);

        return postDto1;

    }

    @Override
    @GetMapping
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> newPage = posts.getContent();

       List<PostDto> content =  newPage.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

       PostResponse postResponse = new PostResponse();

       postResponse.setContent(content);
       postResponse.setPageNo(posts.getNumber());
       postResponse.setPageSize(posts.getSize());
       postResponse.setTotalElements(posts.getTotalElements());
       postResponse.setTotalPages(posts.getTotalPages());
       postResponse.setLast(posts.isLast());

       return postResponse;


    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","Id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto,Long id) {

Category category = categoryRepository.findById(postDto.getCategoryId())
        .orElseThrow(()->new ResourceNotFound("Category","Id",postDto.getCategoryId()));
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","Id",id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        return mapToDto(newPost);
    }

    @Override
    public String deletePostById(Long id) {
      postRepository.findById(id).orElseThrow(()->new ResourceNotFound("Post","Id",id));
         postRepository.deleteById(id);
        return "Post deleted successfully with Id : " + id;
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFound("Category","id",categoryId));

        List<Post> posts = postRepository.findByCategoryId(categoryId);

       return posts.stream().map((post)->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public List<Post> getPostByTitle(String query) {
      List<Post> postTitle =  postRepository.searchTitle(query);

        return postTitle;
    }

    //    convert Entity to Dto
    private PostDto mapToDto(Post post){

        PostDto postDto = modelMapper.map(post, PostDto.class);

//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }



//   convert DTO to Entity
    private Post mapToEntity(PostDto postDto){

        Post post = modelMapper.map(postDto,Post.class);

//        Post post =  new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }


}
