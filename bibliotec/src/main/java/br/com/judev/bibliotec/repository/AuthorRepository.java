package br.com.judev.bibliotec.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import br.com.judev.bibliotec.entity.Author;

public interface AuthorRepository extends JpaRepository<Author , Long>{



    
}
