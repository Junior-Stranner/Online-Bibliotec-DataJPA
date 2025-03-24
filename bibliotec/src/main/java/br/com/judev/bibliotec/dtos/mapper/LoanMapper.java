package br.com.judev.bibliotec.dtos.mapper;

import br.com.judev.bibliotec.dtos.responseDto.AuthorResponseDto;
import br.com.judev.bibliotec.dtos.responseDto.LoanResponseDTO;
import br.com.judev.bibliotec.entity.Loan;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class LoanMapper {

    public static Loan toLoan(AuthorResponseDto dto){
        return new ModelMapper().map(dto,Loan.class);
    }

    public static LoanResponseDTO toDto(Loan entity){
        return new ModelMapper().map(entity,LoanResponseDTO.class);
    }


    public static List<LoanResponseDTO> toListDto(List<Loan> Loans) {
        return Loans.stream().map(loan -> toDto(loan)).collect(Collectors.toList());
    }
}
