package br.com.judev.bibliotec.dtos.responseDto;

import lombok.Data;

@Data
public class ZipResponseDto {
    private Long id;
    private String name;
    private Long cityId;
}
