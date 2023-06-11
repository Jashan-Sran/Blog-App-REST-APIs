package com.geggitech.springboot.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, message = "Title should have at least 3 characters ")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Description should have at least 10 characters ")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private long categoryId;


}
