package br.com.judev.bibliotec.dtos.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import br.com.judev.bibliotec.dtos.PageableDto;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableMapper {

    public static PageableDto toDto(@SuppressWarnings("rawtypes") Page page) {
        return new ModelMapper().map(page, PageableDto.class);
    }
    
}