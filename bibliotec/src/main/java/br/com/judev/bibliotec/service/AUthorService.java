package br.com.judev.bibliotec.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.Author;

@Service
public interface AUthorService {
    
    public Author addAuthor(Author author);
    public List<Author> getAuthors();
    public Author getAuthorById(Long authorId);
    public Author getAuthor(Long authorId);
    public Author deleteAuthor(Long authorId);
    public Author editAuthor(Long authorId, Author author);
    public Author addZipcodeToAuthor(Long authorId, Long zipcodeId);
    public Author deleteZipcodeFromAuthor(Long authorId);
}
