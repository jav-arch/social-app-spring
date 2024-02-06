package com.jk.socialapp.services;

import com.jk.socialapp.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);

    public List<CategoryDto> getAllCategories();

    public CategoryDto updateCategory(CategoryDto categoryDto, Integer catId);

    public CategoryDto getCategoryById(Integer catId);

    public void deleteCategory(Integer catId);
}
