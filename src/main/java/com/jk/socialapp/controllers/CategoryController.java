package com.jk.socialapp.controllers;

import com.jk.socialapp.payloads.ApiResponse;
import com.jk.socialapp.payloads.CategoryDto;
import com.jk.socialapp.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
    {
            CategoryDto createdCategory = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId)
    {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,catId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
    {
        categoryService.deleteCategory(catId);
        ApiResponse apiResponse = new ApiResponse("Category Deleted Successfully" , true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDtos = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById (@PathVariable Integer catId){
        CategoryDto categoryDto = categoryService.getCategoryById(catId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

}
