package br.com.judev.bibliotec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.City;

public interface CityRepository extends JpaRepository<City, Long>{
    
}
