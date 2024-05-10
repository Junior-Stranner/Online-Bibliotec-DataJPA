package br.com.judev.bibliotec.dtos.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import br.com.judev.bibliotec.dtos.responseDto.BookResponseDto;
import br.com.judev.bibliotec.entity.Book;

public class BookMapper {
    

      public static Book toAuthor(BookResponseDto dto){
        return new ModelMapper().map(dto,Book.class);
    }

    public static BookResponseDto ToDto(Book entity){
        return new ModelMapper().map(entity,BookResponseDto.class);
    }

    
    public static List<BookResponseDto> toListDto(List<Book> books) {
        return books.stream().map(book -> ToDto(book)).collect(Collectors.toList());
    }
}
