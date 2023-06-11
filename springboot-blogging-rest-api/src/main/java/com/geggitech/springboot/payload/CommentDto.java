package com.geggitech.springboot.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    private Long id;

    @NotEmpty
    @Size(min = 2,message = "Name should have at least 2 characters ")
    private String name;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Body should have at least 10 characters ")
    private String body;

}
