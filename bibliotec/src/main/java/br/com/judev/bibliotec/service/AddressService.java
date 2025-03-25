package br.com.judev.bibliotec.service;

import br.com.judev.bibliotec.dtos.AddressDTO;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    AddressDTO getAddressByPostalCode(String postalCode);
}
