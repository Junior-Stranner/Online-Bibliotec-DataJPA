package br.com.judev.bibliotec.dtos.mapper;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.com.judev.bibliotec.dtos.responseDto.CategoryResponseDto;
import br.com.judev.bibliotec.entity.Category;

public class CategoryMapper {
    
    
    public static Category toCategory(CategoryResponseDto dto){
        return new ModelMapper().map(dto,Category.class);
    }
    public static CategoryResponseDto ToDto(Category entity){
        return new ModelMapper().map(entity,CategoryResponseDto.class);
    }

      
    public static List<CategoryResponseDto> toListDto(List<Category> Categorys) {
        return Categorys.stream().map(category -> ToDto(category)).collect(Collectors.toList());
    }
}

