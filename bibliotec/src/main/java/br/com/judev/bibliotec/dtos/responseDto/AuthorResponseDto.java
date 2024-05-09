package br.com.judev.bibliotec.dtos.responseDto;

import java.util.List;

import lombok.Data;

@Data
public class AuthorResponseDto {
    private Long id;
    private String name;
    private List<String> bookNames;
    private String zipcodeName;
}
