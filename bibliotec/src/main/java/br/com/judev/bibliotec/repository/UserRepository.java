package br.com.judev.bibliotec.repository;

import br.com.judev.bibliotec.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);
}
