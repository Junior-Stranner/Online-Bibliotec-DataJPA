package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.Controller.CategoryController;
import br.com.judev.bibliotec.dtos.mapper.CategoryMapper;
import br.com.judev.bibliotec.dtos.requestDto.CategoryRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.CategoryResponseDto;
import br.com.judev.bibliotec.entity.Category;
import br.com.judev.bibliotec.repository.CategoryRepository;
import br.com.judev.bibliotec.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


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
                System.out.println("Category name is null or blank.");
                throw new IllegalArgumentException("Category name cannot be null or blank.");
            }
            Optional<Category> existingCategory = categoryRepository.findByName(categoryRequestDto.getName());
             if (existingCategory.isPresent()) {
                throw new DuplicateKeyException("Category already exists!");
            }

        Category category = new Category();
        category.setName(categoryRequestDto.getName());
        
        categoryRepository.save(category);

        category.add(linkTo(methodOn(CategoryController.class).getCategory(category.getId())).withSelfRel());

        return CategoryMapper.ToDto(category);
    }

    @Override
    public CategoryResponseDto getCategoryById(Long categoryId) {
        Category category = getCategory(categoryId);
        return CategoryMapper.ToDto(category);
    }

 /*    @Override
    public List<CategoryResponseDto> getCategories(Pageable pageable) {
     List<Category> categories = StreamSupport
                .stream(categoryRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

                categories.forEach(c -> c.add(linkTo(methodOn(CategoryController.class).getCategory(c.getId())).withSelfRel()));

        return CategoryMapper.toListDto(categories);
    }*/
    @Override
    public List<CategoryResponseDto> getCategories(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        List<Category> categories = categoryPage.getContent();
        
        categories.forEach(c -> c.add(linkTo(methodOn(CategoryController.class).getCategory(c.getId())).withSelfRel()));
        return CategoryMapper.toListDto(categories);
    }
    
    @Override
    public CategoryResponseDto deleteCategory(Long categoryId) {
        Category category = getCategory(categoryId);
        categoryRepository.delete(category);

        category.add(linkTo(methodOn(CategoryController.class).getCategory(category.getId())).withSelfRel());

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

        Optional<Category> existingCategory = categoryRepository.findByName(categoryRequestDto.getName());
        if(existingCategory.isPresent() && !existingCategory.get().getId().equals(categoryId)){
            throw new DuplicateKeyException("Another Category with the same Category already exists!");

        }

        // Atualizar a Category com os dados do CategoryRequestDto
        categoryToEdit.setName(categoryRequestDto.getName());
        
        // Salvar as mudanças no repositório
        categoryRepository.save(categoryToEdit);

        categoryToEdit.add(linkTo(methodOn(CategoryController.class).getCategory(categoryToEdit.getId())).withSelfRel());
        // Retornar a Category como DTO
        return CategoryMapper.ToDto(categoryToEdit);
    }
    
    
}
