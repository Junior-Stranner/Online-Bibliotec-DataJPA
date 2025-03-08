package br.com.judev.bibliotec.repository;

import br.com.judev.bibliotec.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
