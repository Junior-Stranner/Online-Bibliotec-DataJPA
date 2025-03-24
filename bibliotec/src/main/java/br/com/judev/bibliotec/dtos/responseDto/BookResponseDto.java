package br.com.judev.bibliotec.dtos.responseDto;

import java.util.List;

import lombok.Data;

@Data
public class BookResponseDto {
    private Long id;
    private String name;
    private boolean avaliable;
    private List<String> authorNames;
    private String categoryName;
}
