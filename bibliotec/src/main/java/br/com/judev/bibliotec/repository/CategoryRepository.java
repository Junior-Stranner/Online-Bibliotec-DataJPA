package br.com.judev.bibliotec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<Category> findByName(String name);
    
}
