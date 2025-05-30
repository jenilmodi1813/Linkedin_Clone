package com.linkedin.Company_Service.controller;

import com.linkedin.Company_Service.dtos.category.CategoryDto;
import com.linkedin.Company_Service.dtos.category.CreateCategoryDto;
import com.linkedin.Company_Service.services.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryDto createCategoryDto) {
        return categoryService.createCategory(createCategoryDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategoryById(@Valid @RequestBody CreateCategoryDto createCategoryDto, @PathVariable("id") Long id) {

        return categoryService.updateCategoryById(createCategoryDto, id);
    }

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("id") Long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
        return categoryService.deleteCategoryById(id);
    }
}
