package br.com.judev.bibliotec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.City;
import java.util.Optional;


public interface CityRepository extends JpaRepository<City, Long>{
    
    Optional<City> findByName(String name);
}
