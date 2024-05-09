package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.mapper.AuthorMapper;
import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.repository.AuthorRepository;
import br.com.judev.bibliotec.service.AuthorService;
import br.com.judev.bibliotec.service.ZipcodeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final ZipcodeService zipcodeService;

    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
     
       if(authorRequestDto == null || authorRequestDto.getName() == null || authorRequestDto.getName().isBlank()){
           System.out.println("City name is null or blank.");
           throw new IllegalArgumentException("City name cannot be null or blank.");
         }

         Author author = new Author();
        author.setName(authorRequestDto.getName());
        
        authorRepository.save(author);
        return AuthorMapper.ToDto(author);
    }
    @Override
    public List<AuthorResponseDto> getAuthors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthors'");
    }
    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorById'");
    }
    @Override
    public Author getAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthor'");
    }
    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAuthor'");
    }
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editAuthor'");
    }
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addZipcodeToAuthor'");
    }
    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteZipcodeFromAuthor'");
    }

    
    
}
