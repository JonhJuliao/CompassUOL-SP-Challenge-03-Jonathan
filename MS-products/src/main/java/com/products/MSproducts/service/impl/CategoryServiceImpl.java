package com.products.MSproducts.service.impl;

import com.products.MSproducts.entity.Category;
import com.products.MSproducts.payload.CategoryDto;
import com.products.MSproducts.repository.CategoryRepository;
import com.products.MSproducts.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ModelMapper mapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        var category = mapToEntity(categoryDto);
        var savedCategory = categoryRepository.save(category);

        return mapToDto(savedCategory);
    }

    private CategoryDto mapToDto(Category category) {
        return mapper.map(category, CategoryDto.class);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        return mapper.map(categoryDto, Category.class);
    }
}
