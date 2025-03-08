package br.com.judev.bibliotec.dtos.mapper;

import br.com.judev.bibliotec.dtos.requestDto.CreateUserRequestDTO;
import br.com.judev.bibliotec.dtos.responseDto.CreateUserResponseDTO;
import br.com.judev.bibliotec.entity.User;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toUser(CreateUserRequestDTO requestDTO){
        return new ModelMapper().map(requestDTO, User.class);
    }

    public static CreateUserResponseDTO toDto(User user){
        return new ModelMapper().map(user, CreateUserResponseDTO.class);
    }

    public List<CreateUserResponseDTO> toDtoList(List<User> users){
        return users.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
