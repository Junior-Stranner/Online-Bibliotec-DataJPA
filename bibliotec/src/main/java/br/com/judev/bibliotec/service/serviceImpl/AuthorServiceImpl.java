package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.mapper.AuthorMapper;
import br.com.judev.bibliotec.dtos.requestDto.AuthorRequestDto;
import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.entity.Author;
import br.com.judev.bibliotec.entity.Zipcode;
import br.com.judev.bibliotec.repository.AuthorRepository;
import br.com.judev.bibliotec.repository.ZipcodeRepository;
import br.com.judev.bibliotec.service.AuthorService;
import br.com.judev.bibliotec.service.ZipcodeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final ZipcodeRepository zipcodeRepository;
    private final ZipcodeService zipcodeService;

    @Override
    public AuthorResponseDto addAuthor(AuthorRequestDto authorRequestDto) {
        // Corrigir mensagem de erro e validar antes de instanciar
        if (authorRequestDto == null || authorRequestDto.getName() == null || authorRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or blank."); // Mensagem corrigida
        }
    
        if (authorRequestDto.getZipcodeId() == null) {
            throw new IllegalArgumentException("Zipcode ID must be provided."); // Mensagem corrigida
        }
    
        // Verificar se o Zipcode existe
        Optional<Zipcode> optionalZipcode = zipcodeRepository.findById(authorRequestDto.getZipcodeId());
        if (!optionalZipcode.isPresent()) {
            throw new IllegalArgumentException("Zipcode not found with ID: " + authorRequestDto.getZipcodeId());
        }
    
        // Verificar se o Zipcode já está associado a outro autor
        Zipcode zipcode = optionalZipcode.get(); // Pegar o Zipcode após validação
    
        // Instanciar o Author após validação
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setZipcode(zipcode);
    
        // Salvar no repositório
        authorRepository.save(author);
    
        // Retornar como DTO
        return AuthorMapper.ToDto(author);
    }
    

    @Override
    public List<AuthorResponseDto> getAuthors() {
         List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return AuthorMapper.toListDto(authors);
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
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("could not find Author with id: " + authorId));
// Verificar se o autor tem um código postal associado
      if (author.getZipcode() != null) {
              throw new IllegalArgumentException("Cannot delete Author with ID: " + authorId + 
                                       " because it's associated with a Zipcode."); 
     }
        authorRepository.delete(author);
        return AuthorMapper.ToDto(author);
    }
    
    @Override
    public AuthorResponseDto editAuthor(Long authorId, AuthorRequestDto authorRequestDto) {
        Author edidAuthor = authorRepository.findById(authorId)
        .orElseThrow(() -> new EntityNotFoundException("Author with ID: " + authorId + " could not be found"));

        if(authorRequestDto == null || authorRequestDto.getName() == null || authorRequestDto.getName().isBlank()){
            System.out.println("Author name is null or blank.");
            throw new IllegalArgumentException("Author name cannot be null or blank.");
        }
       
        edidAuthor.setName(authorRequestDto.getName());

        authorRepository.save(edidAuthor);
        return AuthorMapper.ToDto(edidAuthor);
    }
    @Override
    public AuthorResponseDto addZipcodeToAuthor(Long authorId, Long zipcodeId) {
      Author author = getAuthor(authorId);
        if (author == null) {
            throw new EntityNotFoundException("author with ID " + authorId + " not found.");
        }
    
        Zipcode zipcode = zipcodeService.getZipcode(zipcodeId);
        if (zipcode == null) {
            throw new EntityNotFoundException("Zipcode with ID " + zipcodeId + " not found.");
        }
    
        if (author.getZipcode() != null) {
            throw new IllegalArgumentException("Zipcode already has a author.");
        }
    
        author.setZipcode(zipcode);
        authorRepository.save(author);

        return AuthorMapper.ToDto(author);
    }
    @Override
    public AuthorResponseDto deleteZipcodeFromAuthor(Long authorId) {
      
        Author author = getAuthor(authorId);
        if(author == null){
            throw new EntityNotFoundException("Zipcode with ID " + authorId + " not found.");
        }

        if(author.getZipcode() == null){
            throw new IllegalArgumentException("Author does not have a Zipcode to remove.");
        }

        author.setZipcode(null);
    
        return AuthorMapper.ToDto(author);
    }
    
}
