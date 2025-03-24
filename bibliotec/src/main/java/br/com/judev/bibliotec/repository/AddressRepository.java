package br.com.judev.bibliotec.repository;

import br.com.judev.bibliotec.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
