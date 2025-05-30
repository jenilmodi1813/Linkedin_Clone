package com.linkedin.Company_Service.services.impl;

import com.linkedin.Company_Service.dtos.category.CategoryDto;
import com.linkedin.Company_Service.dtos.category.CreateCategoryDto;
import com.linkedin.Company_Service.entities.Category;
import com.linkedin.Company_Service.exception.ApiException;
import com.linkedin.Company_Service.repositories.CategoryRepository;
import com.linkedin.Company_Service.services.CategoryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<Void> createCategory(CreateCategoryDto createCategoryDto) {
        Optional<Category> byName = categoryRepository.findByName(createCategoryDto.getName());
        if (byName.isPresent()) {
            throw new ApiException("Category with this name already exists", HttpStatus.BAD_REQUEST);
        }
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        categoryRepository.save(category);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> updateCategoryById(CreateCategoryDto createCategoryDto, Long id) {
        Category byId = categoryRepository.findById(id).orElseThrow(() -> new ApiException("category with id " + id + " was not found", HttpStatus.NOT_FOUND));

        byId.setName(createCategoryDto.getName());
        categoryRepository.save(byId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<CategoryDto> getCategoryById(Long id) {
        Category byId = categoryRepository.findById(id).orElseThrow(() -> new ApiException("category with id " + id + " was not found", HttpStatus.NOT_FOUND));

        return ResponseEntity.ok().body(mapToDto(byId));
    }

    @Override
    public ResponseEntity<Void> deleteCategoryById(Long id) {
        Category byId = categoryRepository.findById(id).orElseThrow(() -> new ApiException("category with id " + id + " was not found", HttpStatus.NOT_FOUND));
        categoryRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDto> dto = all.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(dto);
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }
}
