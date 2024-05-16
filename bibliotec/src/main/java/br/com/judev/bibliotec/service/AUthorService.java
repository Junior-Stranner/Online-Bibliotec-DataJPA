package br.com.judev.bibliotec.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;

@Service
public interface AuthorService {

    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto);
    public List<AuthorResponseDto> getAuthors(Pageable pageable);
    public AuthorResponseDto getAuthorById(Long authorId);
    public Author getAuthor(Long authorId);
    public AuthorResponseDto deleteAuthor(Long authorId);
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto);
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId);
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId);
}
