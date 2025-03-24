package br.com.judev.bibliotec.dtos.mapper;

import br.com.judev.bibliotec.dtos.AddressDTO;
import br.com.judev.bibliotec.entity.Address;
import org.modelmapper.ModelMapper;

public class AddressMapper {

    public static Address toEntity(AddressDTO dto){
        return new ModelMapper().map(dto, Address.class);
    }

    public static AddressDTO toDTO(Address entity){
       return new ModelMapper().map(entity, AddressDTO.class);
    }
}
