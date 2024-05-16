package br.com.judev.bibliotec.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.judev.bibliotec.entity.City;

import java.util.Optional;


public interface CityRepository extends JpaRepository<City, Long>{

   //  @Query("SELECT c FROM City c order by c.name asc")
    @SuppressWarnings("null")
    Page<City> findAll( Pageable pageable);
    
    Optional<City> findByName(String name);
}
