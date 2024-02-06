package com.jk.socialapp.services;

import com.jk.socialapp.exceptions.ResourceNotFoundException;
import com.jk.socialapp.models.Category;
import com.jk.socialapp.payloads.CategoryDto;
import com.jk.socialapp.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory= categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((category) -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new ResourceNotFoundException("Category", "Id", catId));
        category.setCategoryName(categoryDto.getCategoryName());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",catId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",catId));
        categoryRepository.delete(category);
    }

}
