package com.geggitech.springboot.service;

import com.geggitech.springboot.payload.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto findByCategoryId(Long categoryId);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto );

    void deleteById(Long id);

    List<CategoryDto> getAllCategories();
}
