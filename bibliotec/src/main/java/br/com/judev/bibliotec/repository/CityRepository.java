package br.com.judev.bibliotec.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.repository.Projection.CityProjection;

import java.util.Optional;


public interface CityRepository extends JpaRepository<City, Long>{

     @Query("SELECT c FROM City c order by c.name asc")
    Page<CityProjection> findAllPageable(Pageable pageable);
    
    
    
    Optional<City> findByName(String name);
}
