package br.com.judev.bibliotec.dtos.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ZipcodeRequestDto {
  @NotBlank
  private String code;
  private Long cityId;
}

