package br.com.judev.bibliotec.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.City;

@Service
public interface CityService {
    public City addCity(City city);
    public List<City> getCities();
    public City getCity(Long cityId);
    public City deleteCity(Long cityId);
    public City editCity(Long cityId, City city);

}  