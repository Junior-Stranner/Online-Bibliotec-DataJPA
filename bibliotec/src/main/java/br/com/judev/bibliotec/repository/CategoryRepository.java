package br.com.judev.bibliotec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.Category;
import br.com.judev.bibliotec.entity.City;

public interface CategoryRepository extends JpaRepository<Category, Long>{

    Optional<City> findByName(String name);
    
}
