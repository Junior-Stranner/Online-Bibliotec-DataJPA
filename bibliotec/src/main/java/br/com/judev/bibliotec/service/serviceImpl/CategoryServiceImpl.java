package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.mapper.CategoryMapper;
import br.com.judev.bibliotec.dtos.requestDto.CategoryRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.CategoryResponseDto;
import br.com.judev.bibliotec.entity.Category;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.repository.CategoryRepository;
import br.com.judev.bibliotec.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

     private final CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
        new IllegalArgumentException("could not find category with id: " + categoryId));
    }

    @Override
    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto) {

          // Verificar se a entrada e o nome da cidade são válidos
            if (categoryRequestDto == null || categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
                System.out.println("City name is null or blank.");
                throw new IllegalArgumentException("City name cannot be null or blank.");
            }
        
            Optional<City> existingCity = categoryRepository.findByName(categoryRequestDto.getName());
             if (existingCity.isPresent()) {
                throw new DuplicateKeyException("City already exists!");
            }

        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        
        categoryRepository.save(category);
        return CategoryMapper.ToDto(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return CategoryMapper.ToDto(category);
    }

    @Override
    public List<CategoryResponseDto> getCategories() {
     List<Category> categories = StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return CategoryMapper.toListDto(categories);
    }

    @Override
    public CategoryResponseDto deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        categoryRepository.delete(category);
        return CategoryMapper.ToDto(category);
    }

    @Override
    public CategoryResponseDto editCategory(Long categoryId, CategoryRequestDto categoryRequestDto) {
        // Verificar se a Category com o ID fornecido existe
        Category categoryToEdit = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("Category with ID: " + categoryId + " could not be found"));
    
        // Verificar se o CategoryRequestDto é válido
        if (categoryRequestDto == null || categoryRequestDto.getName() == null || categoryRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be null or blank!");
        }

        // Atualizar a Category com os dados do CategoryRequestDto
        categoryToEdit.setName(categoryRequestDto.getName());
        
        // Salvar as mudanças no repositório
        categoryRepository.save(categoryToEdit);
    
        // Retornar a Category como DTO
        return CategoryMapper.ToDto(categoryToEdit);
    }
    
    
}
