package br.com.judev.bibliotec.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.judev.bibliotec.entity.City;
import br.com.judev.bibliotec.repository.CityRepository;
import br.com.judev.bibliotec.service.CityService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CItyServiceImpl implements CityService{

        private final CityRepository cityRepository;

    
    @Override
    public City addCity(City city) {
      Optional<City> citys = cityRepository.findById(city.getId());
      return city;
    }

    @Override
    public List<City> getCities() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCities'");
    }

    @Override
    public City getCity(Long cityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCity'");
    }

    @Override
    public City deleteCity(Long cityId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCity'");
    }

    @Override
    public City editCity(Long cityId, City city) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editCity'");
    }
    
}
