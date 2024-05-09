package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.mapper.AuthorMapper;
import br.com.judev.bibliotec.dtos.mapper.CategoryMapper;
import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.Category;
import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.repository.AuthorRepository;
import br.com.judev.bibliotec.service.AuthorService;
import br.com.judev.bibliotec.service.ZipcodeService;
import jakarta.persistence.EntityNotFoundException;
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
       Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("could not find Author with id: " + authorId));
        return AuthorMapper.ToDto(author);
    }
    @Override
    public Author getAuthor(Long authorId) {
       return authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("could not find Author with id: " + authorId));
    }
    @Override
    public AuthorResponseDto deleteAuthor(Long authorId) {
         Author author = getAuthor(authorId);
        authorRepository.delete(author);
        return AuthorMapper.ToDto(author);
    }
    
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        if(authorRequestDto == null || authorRequestDto.getName() == null || authorRequestDto.getName().isBlank()){
            System.out.println("Author name is null or blank.");
            throw new IllegalArgumentException("Author name cannot be null or blank.");
        }

        Author author = new Author();
        author.setName(authorRequestDto.getName());

        authorRepository.save(author);
        return AuthorMapper.ToDto(author);
    }
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
      Author author = getAuthor(zipcodeId);
        if (author == null) {
            throw new EntityNotFoundException("Zipcode with ID " + zipcodeId + " not found.");
        }
    
        Zipcode zipcode = zipcodeService.getZipcode(zipcodeId);
        if (zipcode == null) {
            throw new EntityNotFoundException("City with ID " + zipcodeId + " not found.");
        }
    
        if (zipcode.getCity() != null) {
            throw new IllegalArgumentException("Zipcode already has a city.");
        }
    
        author.setZipcode(zipcode);
        authorRepository.save(author);

        return AuthorMapper.ToDto(author);
    }
    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteZipcodeFromAuthor'");
    }

    
}
