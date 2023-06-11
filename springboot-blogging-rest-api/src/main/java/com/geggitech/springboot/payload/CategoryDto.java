package com.geggitech.springboot.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private long id;

    @NotEmpty()
    @Size(min = 2,message = "name should have at least 2 characters ")
    private String name;

    @NotEmpty()
    @Size(min = 6,message = "Description should have at least 6 characters ")
    private String description;
}
