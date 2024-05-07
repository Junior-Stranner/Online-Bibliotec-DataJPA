package br.com.judev.bibliotec.dtos.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ZipcodeRequestDto {
  @NotBlank
  private String name;
  private Long cityId;
}

