package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.Controller.AuthorController;
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

        if (authorRequestDto == null || authorRequestDto.getName() == null || authorRequestDto.getName().isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or blank."); // Mensagem corrigida
        }
        if (authorRequestDto.getZipcodeId() == null) {
            throw new IllegalArgumentException("Zipcode ID must be provided."); // Mensagem corrigida
        }

        Optional<Zipcode> optionalZipcode = zipcodeRepository.findById(authorRequestDto.getZipcodeId());
        if (!optionalZipcode.isPresent()) {
            throw new IllegalArgumentException("Zipcode not found with ID: " + authorRequestDto.getZipcodeId());
        }
    
        Zipcode zipcode = optionalZipcode.get(); // Pegar o Zipcode após validação
    
        Author author = new Author();
        author.setName(authorRequestDto.getName());
        author.setZipcode(zipcode);
    
        // Salvar no repositório
        authorRepository.save(author);

        author.add(linkTo(methodOn(AuthorController.class).getAuthor(author.getId())).withSelfRel());

        // Retornar como DTO
        return AuthorMapper.ToDto(author);
    }
    

 /*    @Override
    public List<AuthorResponseDto> getAuthors() {
         List<Author> authors = StreamSupport
                .stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

                authors.forEach(a -> a.add(linkTo(methodOn(AuthorController.class).getAuthor(a.getId())).withSelfRel()));

        return AuthorMapper.toListDto(authors);
    }*/

     @Override
    public List<AuthorResponseDto> getAuthors(Pageable pageable) {
        Page<Author> authoPages = authorRepository.findAll(pageable);
        List<Author> authors = authoPages.getContent();
        
        authors.forEach(a -> a.add(linkTo(methodOn(AuthorController.class).getAuthor(a.getId())).withSelfRel()));
        return AuthorMapper.toListDto(authors);
    }


    @Override
    public AuthorResponseDto getAuthorById(Long authorId) {
       Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new IllegalArgumentException("could not find Author with id: " + authorId));

                author.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId)).withSelfRel());

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

        author.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId)).withSelfRel());

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

        edidAuthor.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId)).withSelfRel());

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

        author.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId)).withSelfRel());

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

        author.add(linkTo(methodOn(AuthorController.class).getAuthor(authorId)).withSelfRel());

        return AuthorMapper.ToDto(author);
    }
    
}
