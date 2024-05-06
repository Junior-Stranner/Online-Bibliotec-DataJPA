package br.com.judev.bibliotec.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.judev.bibliotec.entity.Zipcode;

public interface ZipcodeRepository extends JpaRepository<Zipcode,Long>{
    
}
