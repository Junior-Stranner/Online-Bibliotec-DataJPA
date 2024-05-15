package br.com.judev.bibliotec.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.repository.Projection.CityProjection;

@Service
public interface CityService {
    public City addCity(City city);
    public City getCity(Long cityId);
    public City deleteCity(Long cityId);
    public City editCity(Long cityId, City city);
    public Page<CityProjection> getCities(Pageable pageable);

}  