package br.com.judev.bibliotec.dtos.requestDto;

import lombok.Data;

@Data
public class AuthorRequestDto {
    private String name;
    private Long zipcodeId;
}
