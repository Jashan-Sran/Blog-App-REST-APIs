package com.geggitech.springboot.controller;

import com.geggitech.springboot.entity.Post;
import com.geggitech.springboot.payload.PostDto;
import com.geggitech.springboot.payload.PostResponse;
import com.geggitech.springboot.repository.CategoryRepository;
import com.geggitech.springboot.service.CategoryService;
import com.geggitech.springboot.service.PostService;
import com.geggitech.springboot.utils.AppConstants;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/posts")
public class PostController {


    private PostService postService;

    private CategoryService categoryService;

    public PostController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    // create new post

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

//    get all posts form DB

    @GetMapping
    public PostResponse getAllPosts
            (@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
             @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
             @RequestParam(value = "sortBy",defaultValue =  AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir){
        return postService.getAllPosts(pageNo,pageSize, sortBy, sortDir);
    }

//    get post by passing Id

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id){
        return ok(postService.getPostById(id));
    }

//    Update existing post by passing id
@SecurityRequirement(
        name = "Bear Authentication"
)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Long id){
        return  ok(postService.updatePost(postDto,id));

    }

//    Delete post by Id
@SecurityRequirement(
        name = "Bear Authentication"
)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable Long id){
        return ok(postService.deletePostById(id));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getAllPostsByCategoryId(@PathVariable long id){

        List<PostDto> postDtos = postService.getPostsByCategoryId(id);

        return  ResponseEntity.ok(postDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Post>> getPostBYTitle(@RequestParam("query") String query){
        List<Post> posts = postService.getPostByTitle(query);

        return new ResponseEntity<List<Post>>(posts,HttpStatus.OK);
    }

}
