package com.linkedin.Company_Service.services;

import com.linkedin.Company_Service.dtos.category.CategoryDto;
import com.linkedin.Company_Service.dtos.category.CreateCategoryDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    ResponseEntity<Void> createCategory(@Valid CreateCategoryDto createCategoryDto);

    ResponseEntity<Void> updateCategoryById(@Valid CreateCategoryDto createCategoryDto, Long id);

    ResponseEntity<CategoryDto> getCategoryById(Long id);

    ResponseEntity<Void> deleteCategoryById(Long id);

    ResponseEntity<List<CategoryDto>> getAllCategories();
}
