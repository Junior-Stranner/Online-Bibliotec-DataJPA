package br.com.judev.bibliotec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.judev.bibliotec.entity.Zipcode;

public interface ZipcodeRepository extends JpaRepository<Zipcode,Long>{

    Optional<Zipcode> findByName(String name);

   @Query("SELECT z FROM Zipcode z WHERE z.city.id = :cityId")
  Optional<Zipcode> findByCityId(@Param("cityId") Long cityId);

    
}
