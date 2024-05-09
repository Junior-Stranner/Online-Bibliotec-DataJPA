package br.com.judev.bibliotec.dtos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;

public class AuthorMapper {
    

     
    public static Author toAuthor(AuthorResponseDto dto){
        return new ModelMapper().map(dto,Author.class);
    }

    public static AuthorResponseDto ToDto(Author entity){
        return new ModelMapper().map(entity,AuthorResponseDto.class);
    }

    
    public static List<AuthorResponseDto> toListDto(List<Author> authors) {
        return authors.stream().map(author -> ToDto(author)).collect(Collectors.toList());
    }
}
