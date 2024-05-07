package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

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
    public Author addAuthor(Author author) {
        if(author.getZipcode() == null){
            throw new IllegalArgumentException("author need a zipcode");
        }
        return author;
    
    }

    @Override
    public List<Author> getAuthors() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthors'");
    }

    @Override
    public Author getAuthorById(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthorById'");
    }

    @Override
    public Author getAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAuthor'");
    }

    @Override
    public Author deleteAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteAuthor'");
    }

    @Override
    public Author editAuthor(Long authorId, Author author) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editAuthor'");
    }

    @Override
    public Author addZipcodeToAuthor(Long authorId, Long zipcodeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addZipcodeToAuthor'");
    }

    @Override
    public Author deleteZipcodeFromAuthor(Long authorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteZipcodeFromAuthor'");
    }
    
}
