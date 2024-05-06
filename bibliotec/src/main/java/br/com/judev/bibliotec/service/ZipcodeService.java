package br.com.judev.bibliotec.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.dtos.requestDto.ZipcodeRequestDto;
import br.com.judev.bibliotec.entity.Zipcode;

@Service
public interface ZipcodeService {

    public Zipcode addZipcode(ZipcodeRequestDto zipcodeRequestDto);
    public List<Zipcode> getZipcodes();
    public Zipcode getZipcode(Long zipcodeId);
    public Zipcode deleteZipcode(Long zipcodeId);
    public Zipcode editZipcode(Long zipcodeId, Zipcode zipcode);
    public Zipcode addCityToZipcode(Long zipcodeId, Long cityId);
    public Zipcode removeCityFromZipcode(Long zipcodeId);
}
