package br.com.judev.bibliotec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.Book;

public interface BookRepository extends JpaRepository<Book , Long>{
    
}
