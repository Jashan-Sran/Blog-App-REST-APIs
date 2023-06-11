package com.geggitech.springboot.service.Impl;

import com.geggitech.springboot.entity.Category;
import com.geggitech.springboot.exception.ResourceNotFound;
import com.geggitech.springboot.payload.CategoryDto;
import com.geggitech.springboot.repository.CategoryRepository;
import com.geggitech.springboot.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);

       Category saved = categoryRepository.save(category);

        return modelMapper.map(saved,CategoryDto.class);
    }

    @Override
    public CategoryDto findByCategoryId(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).
                orElseThrow(
                ()->new ResourceNotFound("Category","id",categoryId));

        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Category","Id",id));

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        categoryRepository.save(category);

        return modelMapper.map(category,CategoryDto.class);

    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Category","id",id));
        modelMapper.map(category,CategoryDto.class);
      categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> category = categoryRepository.findAll();
         List<CategoryDto> categoryDto1 = category.stream().map(cat -> new CategoryDto(
                 cat.getId(),
                 cat.getName(),
                 cat.getDescription())).collect(Collectors.toList());

        return categoryDto1;
    }
}
