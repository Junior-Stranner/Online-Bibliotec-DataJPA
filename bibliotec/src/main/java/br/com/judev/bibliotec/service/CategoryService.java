package br.com.judev.bibliotec.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.requestDto.CategoryRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.CategoryResponseDto;
import br.com.judev.bibliotec.entity.Category;

@Service
public interface CategoryService {
    public Category getCategory(Long categoryId);
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto);
    public CategoryResponseDto getCategoryById(Long categoryId);
    public List<CategoryResponseDto> getCategories(Pageable pageable);
    public CategoryResponseDto deleteCategory(Long categoryId);
    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto);
}