package br.com.judev.bibliotec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.Book;

public interface BookRepository extends JpaRepository<Book , Long>{

    Optional<Book> findByName(String name);
    
}
