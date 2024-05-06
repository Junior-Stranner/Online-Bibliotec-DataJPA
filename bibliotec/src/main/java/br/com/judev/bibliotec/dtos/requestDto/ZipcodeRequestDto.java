package br.com.judev.bibliotec.dtos.requestDto;

import lombok.Data;

@Data
public class ZipcodeRequestDto {
    private String name;
    private Long cityId;
}
